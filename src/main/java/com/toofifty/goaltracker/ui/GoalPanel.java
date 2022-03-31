package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.TaskUIStatusManager;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskType;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;

public class GoalPanel extends JPanel implements Refreshable
{
    private EditableInput descriptionInput;
    private ListPanel<Task> taskListPanel;
    private Goal goal;

    GoalPanel(GoalTrackerPlugin plugin, Goal goal, Runnable closeListener)
    {
        super();
        this.goal = goal;

        setLayout(new BorderLayout());

        descriptionInput = new EditableInput(goal::setDescription);

        TextButton backButton = new TextButton(
            "< Back", ColorScheme.PROGRESS_ERROR_COLOR);
        backButton.onClick(e -> closeListener.run());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(descriptionInput, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.setBorder(new EmptyBorder(0, 0, 8, 0));

        add(headerPanel, BorderLayout.NORTH);

        taskListPanel = new ListPanel<>(goal, (task) -> {
            ListItemPanel<Task> taskPanel = new ListItemPanel<>(goal, task);
            taskPanel.add(new TaskItemContent(plugin, task));
            taskPanel.setBorder(new EmptyBorder(2, 4, 2, 4));

            if (task.getType() == TaskType.MANUAL) {
                taskPanel.onClick(e -> {
                    ((ManualTask) task).toggle();
                    if (task.check().isCompleted()) {
                        plugin.notifyTask(task);
                    }
                    task.save();
                    TaskUIStatusManager.getInstance().refresh(task);
                });
            }

            return taskPanel;
        });
        taskListPanel.setGap(0);
        taskListPanel.setPlaceholder("No tasks added yet");
        add(taskListPanel, BorderLayout.CENTER);

        NewTaskPanel newTaskPanel = new NewTaskPanel(plugin, goal);
        newTaskPanel.onUpdate(() -> {
            taskListPanel.tryBuildList();
            taskListPanel.refresh();
            plugin.setValidateAll(true);
        });
        add(newTaskPanel, BorderLayout.SOUTH);
    }

    @Override
    public void refresh()
    {
        descriptionInput.setValue(goal.getDescription());
        taskListPanel.refresh();
    }
}
