package com.toofifty.goaltracker;

import com.google.inject.Provides;
import com.toofifty.goaltracker.models.enums.TaskType;
import com.toofifty.goaltracker.models.task.*;
import com.toofifty.goaltracker.services.TaskIconService;
import com.toofifty.goaltracker.services.TaskUpdateService;
import com.toofifty.goaltracker.ui.GoalTrackerPanel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.*;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.game.chatbox.ChatboxItemSearch;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.AsyncBufferedImage;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@PluginDescriptor(name = "Goal Tracker", description = "Keep track of your goals and complete them automatically")
public class GoalTrackerPlugin extends Plugin
{
    public static final int[] PLAYER_INVENTORIES = {
        InventoryID.INVENTORY.getId(),
        InventoryID.EQUIPMENT.getId(),
        InventoryID.BANK.getId(),
        InventoryID.SEED_VAULT.getId(),
        InventoryID.GROUP_STORAGE.getId()
    };

    @Getter
    @Inject
    private Client client;

    @Getter
    @Inject
    private SkillIconManager skillIconManager;

    @Getter
    @Inject
    private ItemManager itemManager;

    @Getter
    @Inject
    private ChatboxItemSearch itemSearch;

    @Inject
    private ClientToolbar clientToolbar;

    @Getter
    @Inject
    private ClientThread clientThread;

    @Getter
    @Inject
    private ItemCache itemCache;

    @Inject
    private ChatMessageManager chatMessageManager;

    @Getter
    @Inject
    private GoalTrackerConfig config;

    @Getter
    @Inject
    private TaskUpdateService taskUpdateService;

    @Getter
    @Inject
    private TaskIconService taskIconService;

    @Getter
    @Inject
    private TaskUIStatusManager uiStatusManager;

    @Getter
    @Inject
    private GoalManager goalManager;

    @Inject
    private GoalTrackerPanel goalTrackerPanel;

    private NavigationButton uiNavigationButton;

    @Setter
    private boolean validateAll = true;

    @Override
    protected void startUp()
    {
        goalManager.load();
        itemCache.load();
        goalTrackerPanel.home();

        final AsyncBufferedImage icon = itemManager.getImage(ItemID.TODO_LIST);

        icon.onLoaded(() -> {
            uiNavigationButton = NavigationButton.builder()
                .tooltip("Goal Tracker")
                .icon(icon)
                .priority(7)
                .panel(goalTrackerPanel)
                .build();

            clientToolbar.addNavigation(uiNavigationButton);
        });

        goalTrackerPanel.onGoalUpdated((goal) -> goalManager.save());
        goalTrackerPanel.onTaskAdded((task) -> {
            if (taskUpdateService.update(task)) {
                if (task.getStatus().isCompleted()) {
                    notifyTask(task);
                }

                uiStatusManager.refresh(task);
            }

            goalManager.save();
        });
        goalTrackerPanel.onTaskUpdated((task) -> goalManager.save());
    }

    @Override
    protected void shutDown()
    {
        clientToolbar.removeNavigation(uiNavigationButton);
    }

    @Subscribe
    public void onSessionOpen(SessionOpen event)
    {
        goalManager.load();
    }

    @Subscribe
    public void onStatChanged(StatChanged event)
    {
        List<SkillLevelTask> skillLevelTasks = goalManager.getIncompleteTasksByType(TaskType.SKILL_LEVEL);
        for (SkillLevelTask task : skillLevelTasks) {
            if (!taskUpdateService.update(task, event)) continue;

            if (task.getStatus().isCompleted()) {
                notifyTask(task);
            }

            uiStatusManager.refresh(task);
            this.goalManager.save();
        }

        List<SkillXpTask> skillXpTasks = goalManager.getIncompleteTasksByType(TaskType.SKILL_XP);
        for (SkillXpTask task : skillXpTasks) {
            if (!taskUpdateService.update(task, event)) continue;

            if (task.getStatus().isCompleted()) {
                notifyTask(task);
            }

            uiStatusManager.refresh(task);
            this.goalManager.save();
        }
    }

    public void notifyTask(Task task)
    {
        if (client.getGameState() != GameState.LOGGED_IN || task.isNotified()) return;

        log.debug("Notify: [Goal Tracker] You have completed a task: " + task + "!");

        String message = "[Goal Tracker] You have completed a task: " + task + "!";
        String formattedMessage = new ChatMessageBuilder().append(ColorScheme.PROGRESS_COMPLETE_COLOR, message).build();
        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.CONSOLE)
            .name("Goal Tracker")
            .runeLiteFormattedMessage(formattedMessage)
            .build());

        task.setNotified(true);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }

        // redo the login check on the next game tick
        validateAll = true;
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (!validateAll) {
            return;
        }
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }

        validateAll = false;
        // perform a full refresh just once on login
        // onGameStateChanged reports incorrect quest statuses,
        // so this need to be done in this subscriber
        goalTrackerPanel.refresh();
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE || !event.getMessage().contains("Quest complete")) return;

        List<QuestTask> questTasks = goalManager.getIncompleteTasksByType(TaskType.QUEST);
        for (QuestTask task : questTasks) {
            if (!taskUpdateService.update(task)) continue;

            if (task.getStatus().isCompleted()) {
                notifyTask(task);
            }

            uiStatusManager.refresh(task);
            this.goalManager.save();
        }
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event)
    {
        if (IntStream.of(GoalTrackerPlugin.PLAYER_INVENTORIES).noneMatch((id) -> id == event.getContainerId())) return;

        itemCache.update(event.getContainerId(), event.getItemContainer().getItems());

        List<ItemTask> itemTasks = goalManager.getIncompleteTasksByType(TaskType.ITEM);
        for (ItemTask task : itemTasks) {
            if (!taskUpdateService.update(task)) continue;

            if (task.getStatus().isCompleted()) {
                notifyTask(task);
            }

            // always refresh item tasks, since the acquired
            // count could have changed
            uiStatusManager.refresh(task);
            this.goalManager.save();
        }
    }

    @Provides
    GoalTrackerConfig getGoalTrackerConfig(ConfigManager configManager)
    {
        return configManager.getConfig(GoalTrackerConfig.class);
    }
}
