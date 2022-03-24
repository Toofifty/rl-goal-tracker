package com.toofifty.goaltracker;

import com.google.inject.Provides;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.SkillXpTask;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.ui.GoalTrackerPanel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.events.StatChanged;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.NavigationButton;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.List;

@Slf4j
@PluginDescriptor(name = "Goal Tracker")
public class GoalTrackerPlugin extends Plugin
{
    @Getter
    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private ChatMessageManager chatMessageManager;

    @Inject
    private GoalTrackerConfig config;

    @Getter
    private GoalManager goalManager;

    private NavigationButton uiNavigationButton;

    private GoalTrackerPanel goalTrackerPanel;

    @Provides
    GoalTrackerConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(GoalTrackerConfig.class);
    }

    @Override
    protected void startUp() throws Exception
    {
        goalManager = new GoalManager(config);
        goalManager.load();

        final BufferedImage icon = itemManager.getImage(ItemID.TODO_LIST);
        goalTrackerPanel = new GoalTrackerPanel(this);

        uiNavigationButton = NavigationButton.builder()
            .tooltip("Goal Tracker")
            .icon(icon)
            .priority(7)
            .panel(goalTrackerPanel)
            .build();

        clientToolbar.addNavigation(uiNavigationButton);
    }

    @Override
    protected void shutDown() throws Exception
    {
        goalManager.save();

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
        System.out.println("onStatChanged " + event.getSkill()
            .getName() + " " + event.getLevel() + " " + event.getXp());
        List<SkillLevelTask> skillLevelTasks = goalManager.getAllTasksOfType(
            Task.TaskType.SKILL_LEVEL);
        for (SkillLevelTask task : skillLevelTasks) {
            if (event.getSkill() == task.getSkill() && event.getLevel() >= task.getLevel()) {
                notifyTask(task);
                TaskUIStatusManager.getInstance().refresh(task);
            }
        }

        List<SkillXpTask> skillXpTasks = goalManager.getAllTasksOfType(
            Task.TaskType.SKILL_XP);
        for (SkillXpTask task : skillXpTasks) {
            if (event.getSkill() == task.getSkill() && event.getXp() >= task.getXp()) {
                notifyTask(task);
                TaskUIStatusManager.getInstance().refresh(task);
            }
        }
    }

    public void notifyTask(Task task)
    {
        if (client.getGameState() != GameState.LOGGED_IN || task.hasBeenNotified()) {
            return;
        }

        System.out.println(
            "Notify: " + "[Goal Tracker] You have completed a task: " + task.toString() + "!");

        String message = "[Goal Tracker] You have completed a task: " + task.toString() + "!";
        String formattedMessage = new ChatMessageBuilder().append(
            ColorScheme.PROGRESS_COMPLETE_COLOR, message).build();
        chatMessageManager.queue(QueuedMessage.builder()
            .type(ChatMessageType.CONSOLE)
            .name("Goal Tracker")
            .runeLiteFormattedMessage(formattedMessage)
            .build());

        task.hasBeenNotified(true);
    }

    //    @Subscribe
    //    public void onGameStateChanged(GameStateChanged event)
    //    {
    //        if (client.getGameState() != GameState.LOGGED_IN) {
    //            return;
    //        }
    //
    //        goalTrackerPanel.revalidate();
    //    }
    //
    //    @Subscribe
    //    public void onGameTick(GameTick event)
    //    {
    //        System.out.println("Game tick");
    //        System.out.println(client.isClientThread());
    //    }
}
