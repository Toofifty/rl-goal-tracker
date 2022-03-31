package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;
import com.toofifty.goaltracker.goal.factory.ManualTaskFactory;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

public class ManualTaskInput extends TaskInput
{
    private FlatTextField titleField;
    private Goal goal;

    public ManualTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, "Quick add");
        this.goal = goal;

        titleField = new FlatTextField();
        titleField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        titleField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && titleField.getText()
                    .length() > 0) {
                    onSubmit();
                }
            }
        });

        getInputRow().add(titleField, BorderLayout.CENTER);
    }

    @Override
    protected void onSubmit()
    {
        if (titleField.getText().isEmpty()) {
            return;
        }

        ManualTask task = new ManualTaskFactory(plugin, goal).create(
            titleField.getText(), false);
        goal.add(task);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        titleField.setText("");
        titleField.requestFocusInWindow();
    }
}
