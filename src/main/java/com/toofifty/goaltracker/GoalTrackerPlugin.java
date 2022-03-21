package com.toofifty.goaltracker;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import com.toofifty.goaltracker.goal.GoalSet;
import com.toofifty.goaltracker.ui.GoalTrackerPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.events.SessionOpen;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "Goal Tracker"
)
public class GoalTrackerPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private GoalTrackerConfig config;

    private GoalSerializer goalSerializer;

    private NavigationButton uiNavigationButton;

    private List<GoalSet> goalSets;

    @Provides
    GoalTrackerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(GoalTrackerConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        goalSerializer = new GoalSerializer();

        try {
            goalSets = goalSerializer.deserialize(config.goaltrackerData());
        } catch (IllegalStateException e) {
            goalSets = new ArrayList<>();
        }

        final BufferedImage icon = itemManager.getImage(ItemID.TODO_LIST);
        final GoalTrackerPanel uiPanel = new GoalTrackerPanel(goalSets);

        uiNavigationButton = NavigationButton.builder()
                .tooltip("Goal Tracker")
                .icon(icon)
                .priority(7)
                .panel(uiPanel)
                .build();

        clientToolbar.addNavigation(uiNavigationButton);
    }

    @Override
    protected void shutDown() throws Exception {
        clientToolbar.removeNavigation(uiNavigationButton);

        config.goaltrackerData(goalSerializer.serialize(goalSets));
    }

    @Subscribe
    public void onSessionOpen(SessionOpen event) {
        goalSets = goalSerializer.deserialize(config.goaltrackerData());
    }
}
