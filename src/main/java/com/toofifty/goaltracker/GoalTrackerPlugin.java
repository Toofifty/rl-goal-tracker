package com.toofifty.goaltracker;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Provides;
import com.toofifty.goaltracker.ui.GoalTrackerPanel;
import lombok.Getter;
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

    @Getter
    private GoalManager goalManager;

    private NavigationButton uiNavigationButton;

    @Provides
    GoalTrackerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(GoalTrackerConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        goalManager = new GoalManager(config);
        goalManager.load();

        final BufferedImage icon = itemManager.getImage(ItemID.TODO_LIST);
        final GoalTrackerPanel uiPanel = new GoalTrackerPanel(this);

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
        goalManager.save();

        clientToolbar.removeNavigation(uiNavigationButton);
    }

    @Subscribe
    public void onSessionOpen(SessionOpen event) {
        goalManager.load();
    }
}
