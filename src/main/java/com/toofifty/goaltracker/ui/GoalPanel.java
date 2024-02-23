package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.enums.TaskType;
import com.toofifty.goaltracker.models.task.ManualTask;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.ui.components.EditableInput;
import com.toofifty.goaltracker.ui.components.ListItemPanel;
import com.toofifty.goaltracker.ui.components.ListPanel;
import com.toofifty.goaltracker.ui.components.TextButton;
import java.awt.BorderLayout;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;

public class GoalPanel extends JPanel implements Refreshable
{
    private final GoalTrackerPlugin plugin;
    private final Goal goal;

    private final EditableInput descriptionInput;
    private final ListPanel<Task> taskListPanel;
    private Consumer<Goal> goalUpdatedListener;
    private Consumer<Task> taskAddedListener;
    private Consumer<Task> taskUpdatedListener;

    GoalPanel(GoalTrackerPlugin plugin, Goal goal, Runnable closeListener)
    {
        super();
        this.plugin = plugin;
        this.goal = goal;

        setLayout(new BorderLayout());

        TextButton backButton = new TextButton("< Back", ColorScheme.PROGRESS_ERROR_COLOR);
        backButton.onClick((e) -> closeListener.run());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.setBorder(new EmptyBorder(0, 0, 8, 0));
        add(headerPanel, BorderLayout.NORTH);

        descriptionInput = new EditableInput((value) -> {
            goal.setDescription(value);
            this.goalUpdatedListener.accept(goal);
        });
        headerPanel.add(descriptionInput, BorderLayout.CENTER);

        taskListPanel = new ListPanel<>(goal.getTasks(), (task) -> {
            ListItemPanel<Task> taskPanel = new ListItemPanel<>(goal.getTasks(), task);
            taskPanel.add(new TaskItemContent(plugin, task));
            taskPanel.setBorder(new EmptyBorder(2, 4, 2, 4));

            if (TaskType.MANUAL.equals(task.getType())) {
                ManualTask manualTask = (ManualTask) task;

                taskPanel.onClick(e -> {
                    manualTask.toggle();

                    if (task.getStatus().isCompleted()) {
                        plugin.notifyTask(task);
                    }

                    plugin.getUiStatusManager().refresh(task);
                    this.taskUpdatedListener.accept(manualTask);
                });
            }

            taskPanel.onIndented(e -> {
                task.indent();
                this.taskUpdatedListener.accept(task);
                plugin.getUiStatusManager().refresh(task);
            });

            taskPanel.onUnindented(e -> {
                task.unindent();
                this.taskUpdatedListener.accept(task);
                plugin.getUiStatusManager().refresh(task);
            });

            return taskPanel;
        });
        taskListPanel.setGap(0);
        taskListPanel.setPlaceholder("No tasks added yet");
        add(taskListPanel, BorderLayout.CENTER);

        NewTaskPanel newTaskPanel = new NewTaskPanel(plugin, goal);
        newTaskPanel.onTaskAdded(this::updateFromNewTask);
        add(newTaskPanel, BorderLayout.SOUTH);
    }

    public void updateFromNewTask(Task task)
    {
        taskListPanel.tryBuildList();
        taskListPanel.refresh();
        plugin.setValidateAll(true);

        if (Objects.nonNull(this.taskAddedListener)) this.taskAddedListener.accept(task);
        if (Objects.nonNull(this.taskUpdatedListener)) this.taskUpdatedListener.accept(task);
    }

    @Override
    public void refresh()
    {
        descriptionInput.setValue(goal.getDescription());
        taskListPanel.refresh();
    }

    public void onGoalUpdated(Consumer<Goal> listener)
    {
        this.goalUpdatedListener = listener;
    }

    public void onTaskAdded(Consumer<Task> listener)
    {
        this.taskAddedListener = listener;

        taskListPanel.onUpdated(this.taskAddedListener);
    }

    public void onTaskUpdated(Consumer<Task> listener)
    {
        this.taskUpdatedListener = listener;

        taskListPanel.onUpdated(this.taskUpdatedListener);
    }
}
