package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalManager;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.ui.components.ListItemPanel;
import com.toofifty.goaltracker.ui.components.ListPanel;
import com.toofifty.goaltracker.ui.components.TextButton;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

@Singleton
public class GoalTrackerPanel extends PluginPanel implements Refreshable
{
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final ListPanel<Goal> goalListPanel;
    private final GoalTrackerPlugin plugin;
    private GoalPanel goalPanel;
    private Consumer<Goal> goalAddedListener;
    private Consumer<Goal> goalUpdatedListener;
    private Consumer<Task> taskAddedListener;
    private Consumer<Task> taskUpdatedListener;

    @Inject
    public GoalTrackerPanel(GoalTrackerPlugin plugin, GoalManager goalManager)
    {
        super(false);
        this.plugin = plugin;

        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8, 8, 8, 8));

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

        titlePanel.add(
            new TextButton("+ Add goal",
                e -> {
                    Goal goal = goalManager.createGoal();
                    view(goal);

                    if (Objects.nonNull(this.goalAddedListener)) this.goalAddedListener.accept(goal);
                    if (Objects.nonNull(this.goalUpdatedListener)) this.goalUpdatedListener.accept(goal);
                }
            ).narrow(), BorderLayout.EAST);

        JLabel title = new JLabel();
        title.setText("Goal Tracker");
        title.setForeground(Color.WHITE);
        title.setFont(FontManager.getRunescapeBoldFont());
        titlePanel.add(title, BorderLayout.WEST);

        goalListPanel = new ListPanel<>(goalManager.getGoals(),
            (goal) -> new ListItemPanel<>(goalManager.getGoals(), goal).onClick(e -> this.view(goal))
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

        this.goalPanel = new GoalPanel(plugin, goal, this::home);

        this.goalPanel.onGoalUpdated(this.goalUpdatedListener);
        this.goalPanel.onTaskAdded(this.taskAddedListener);
        this.goalPanel.onTaskUpdated(this.taskUpdatedListener);

        add(this.goalPanel, BorderLayout.CENTER);
        this.goalPanel.refresh();

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

        this.goalPanel = null;
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

    public void onGoalUpdated(Consumer<Goal> listener)
    {
        this.goalUpdatedListener = listener;

        this.goalListPanel.onUpdated(this.goalUpdatedListener);

        if (this.goalPanel != null) {
            this.goalPanel.onGoalUpdated(this.goalUpdatedListener);
        }
    }

    public void onTaskUpdated(Consumer<Task> listener)
    {
        this.taskUpdatedListener = listener;

        if (this.goalPanel != null) {
            this.goalPanel.onTaskUpdated(this.taskUpdatedListener);
        }
    }

    public void onTaskAdded(Consumer<Task> listener)
    {
        this.taskAddedListener = listener;

        if (this.goalPanel != null) {
            this.goalPanel.onTaskAdded(this.taskAddedListener);
        }
    }
}
