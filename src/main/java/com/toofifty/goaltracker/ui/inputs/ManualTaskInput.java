package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.ManualTask;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManualTaskInput extends TaskInput<ManualTask>
{
    private final FlatTextField titleField;

    public ManualTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal, "Quick add");

        titleField = new FlatTextField();
        titleField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        titleField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() != KeyEvent.VK_ENTER) return;

                submit();
            }
        });

        getInputRow().add(titleField, BorderLayout.CENTER);
    }

    @Override
    protected void submit()
    {
        if (titleField.getText().isEmpty()) {
            return;
        }

        this.addTask(ManualTask.builder().description(titleField.getText()).build());
    }

    @Override
    protected void reset()
    {
        titleField.setText("");
        titleField.requestFocusInWindow();
    }
}
