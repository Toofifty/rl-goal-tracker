package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;

import javax.swing.*;
import java.awt.*;

import static com.toofifty.goaltracker.utils.Constants.STATUS_TO_COLOR;

public class GoalItemContent extends JPanel implements Refreshable
{
    private final JLabel title = new JLabel();
    private final JLabel progress = new JLabel();

    private final Goal goal;

    GoalItemContent(GoalTrackerPlugin plugin, Goal goal)
    {
        super(new BorderLayout());
        this.goal = goal;

        add(title, BorderLayout.WEST);
        add(progress, BorderLayout.EAST);

        plugin.getUiStatusManager().addRefresher(goal, this::refresh);
    }

    @Override
    public void refresh()
    {
        Color color = STATUS_TO_COLOR.get(goal.getStatus());

        title.setText(goal.getDescription());
        title.setForeground(color);

        progress.setText(
            goal.getComplete().size() + "/" + goal.getTasks().size());
        progress.setForeground(color);
    }
}
