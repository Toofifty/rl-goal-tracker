package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalManager;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GoalTrackerPanel extends PluginPanel {
    private GoalTrackerPlugin plugin;

    private JPanel goalPanel = new JPanel(new BorderLayout());
    private ListPanel<Goal> goalListPanel;

    public GoalTrackerPanel(GoalTrackerPlugin plugin) {
        super();
        this.plugin = plugin;
        GoalManager goalManager = plugin.getGoalManager();

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());

        titlePanel.add(
                new TextButton(
                        "+ Add goal",
                        e -> view(goalManager.create())
                ).narrow(),
                BorderLayout.EAST
        );

        JLabel title = new JLabel();
        title.setText("Goal Tracker");
        title.setForeground(Color.WHITE);
        title.setFont(FontManager.getRunescapeBoldFont());
        titlePanel.add(title, BorderLayout.WEST);

        goalListPanel = new ListPanel<>(
                goalManager,
                (goal) -> new ListItemPanel<>(goalManager, goal)
                        .onClick(e -> this.view(goal))
                        .add(new GoalItemContent(goal))
        );
        goalListPanel.setGap(0);
        goalListPanel.setPlaceholder("Add a new goal using the button above");

        goalPanel.add(titlePanel);
        goalPanel.add(goalListPanel, BorderLayout.SOUTH);
        add(goalPanel);
    }

    public void home() {
        removeAll();
        add(goalPanel);
        goalListPanel.rebuild();

        revalidate();
    }

    public void view(Goal goal) {
        removeAll();
        add(new GoalPanel(goal, this::home));

        revalidate();
    }
}
