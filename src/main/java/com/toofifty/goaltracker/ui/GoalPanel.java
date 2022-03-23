package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GoalPanel extends JPanel {
    private EditableInput descriptionInput;
    private ListPanel<Task> taskListPanel;
    private Goal goal;

    GoalPanel(Goal goal, Runnable closeListener) {
        super();

        this.goal = goal;

        setLayout(new BorderLayout());

        descriptionInput = new EditableInput(goal::setDescription);
        descriptionInput.setValue(goal.getDescription());

        TextButton backButton = new TextButton("< Back", ColorScheme.PROGRESS_ERROR_COLOR);
        backButton.onClick(e -> closeListener.run());

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(descriptionInput, BorderLayout.CENTER);
        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.setBorder(new EmptyBorder(0, 0, 8, 0));

        add(headerPanel, BorderLayout.NORTH);

        taskListPanel = new ListPanel<>(
                goal,
                (task) -> {
                    ListItemPanel<Task> taskPanel = new ListItemPanel<>(goal, task);
                    taskPanel.add(new TaskItemContent(task));
                    taskPanel.setBorder(new EmptyBorder(2, 4, 2, 4));

                    if (task.getType() == Task.TaskType.MANUAL) {
                        taskPanel.onClick(e -> {
                            ((ManualTask) task).toggle();
                            task.save();
                            taskListPanel.rebuild();
                        });
                    }

                    return taskPanel;
                }
        );
        taskListPanel.setGap(0);
        taskListPanel.setPlaceholder("No tasks added yet");
        add(taskListPanel, BorderLayout.CENTER);

        NewTaskPanel newTaskPanel = new NewTaskPanel(goal);
        newTaskPanel.onUpdate(taskListPanel::rebuild);
        add(newTaskPanel, BorderLayout.SOUTH);
    }
}
