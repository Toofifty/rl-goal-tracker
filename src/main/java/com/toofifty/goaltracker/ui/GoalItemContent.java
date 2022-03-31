package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.TaskUIStatusManager;
import com.toofifty.goaltracker.goal.Goal;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public class GoalItemContent extends JPanel implements Refreshable
{
    private final JLabel title = new JLabel();
    private final JLabel progress = new JLabel();

    private final GoalTrackerPlugin plugin;
    private final Goal goal;

    GoalItemContent(GoalTrackerPlugin plugin, Goal goal)
    {
        super(new BorderLayout());
        this.plugin = plugin;
        this.goal = goal;

        add(title, BorderLayout.WEST);
        add(progress, BorderLayout.EAST);

        TaskUIStatusManager.getInstance().addRefresher(goal, this::refresh);
    }

    @Override
    public void refresh()
    {
        Boolean isComplete = goal.isComplete();
        Boolean isInProgress = goal.isInProgress();

        Color color = isComplete ? ColorScheme.PROGRESS_COMPLETE_COLOR
            : isInProgress
                ? ColorScheme.PROGRESS_INPROGRESS_COLOR
                : ColorScheme.PROGRESS_ERROR_COLOR;

        title.setText(goal.getDescription());
        title.setForeground(color);

        progress.setText(
            goal.getComplete().size() + "/" + goal.getTasks().size());
        progress.setForeground(color);
    }
}
