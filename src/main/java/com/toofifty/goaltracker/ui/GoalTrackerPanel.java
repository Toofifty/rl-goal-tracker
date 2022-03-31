package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalManager;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

public class GoalTrackerPanel extends PluginPanel implements Refreshable
{
    private final GoalTrackerPlugin plugin;

    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final ListPanel<Goal> goalListPanel;

    public GoalTrackerPanel(GoalTrackerPlugin plugin)
    {
        super(false);
        this.plugin = plugin;
        GoalManager goalManager = plugin.getGoalManager();

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());

        titlePanel.add(
            new TextButton("+ Add goal",
                e -> view(goalManager.createGoal())
            ).narrow(), BorderLayout.EAST);

        JLabel title = new JLabel();
        title.setText("Goal Tracker");
        title.setForeground(Color.WHITE);
        title.setFont(FontManager.getRunescapeBoldFont());
        titlePanel.add(title, BorderLayout.WEST);

        goalListPanel = new ListPanel<>(goalManager,
            (goal) -> new ListItemPanel<>(
                goalManager, goal).onClick(e -> this.view(goal))
                .add(new GoalItemContent(plugin, goal))
        );
        goalListPanel.setGap(0);
        goalListPanel.setPlaceholder("Add a new goal using the button above");

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(goalListPanel, BorderLayout.CENTER);

        home();
    }

    public void view(Goal goal)
    {
        removeAll();
        GoalPanel goalPanel = new GoalPanel(plugin, goal, this::home);
        add(goalPanel, BorderLayout.CENTER);
        goalPanel.refresh();

        revalidate();
        repaint();
    }

    public void home()
    {
        removeAll();
        add(mainPanel, BorderLayout.CENTER);
        goalListPanel.tryBuildList();
        goalListPanel.refresh();

        revalidate();
        repaint();
    }

    @Override
    public void refresh()
    {
        // refresh single-view goal
        for (Component component : getComponents()) {
            if (component instanceof Refreshable) {
                ((Refreshable) component).refresh();
            }
        }

        goalListPanel.refresh();
    }
}
