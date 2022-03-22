package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.Goal;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;

public class GoalItemContent extends JPanel {
    GoalItemContent(Goal goal) {
        super(new BorderLayout());

        JLabel title = new JLabel();
        title.setText(goal.getDescription());
        title.setForeground(
                goal.isComplete()
                        ? ColorScheme.PROGRESS_COMPLETE_COLOR
                        : goal.isInProgress()
                        ? ColorScheme.PROGRESS_INPROGRESS_COLOR
                        : ColorScheme.PROGRESS_ERROR_COLOR
        );
        add(title, BorderLayout.WEST);

        JLabel progress = new JLabel();
        progress.setText(goal.getComplete().size() + "/" + goal.getTasks().size());
        progress.setForeground(
                goal.isComplete()
                        ? ColorScheme.PROGRESS_COMPLETE_COLOR
                        : goal.isInProgress()
                        ? ColorScheme.PROGRESS_INPROGRESS_COLOR
                        : ColorScheme.PROGRESS_ERROR_COLOR
        );
        add(progress, BorderLayout.EAST);
    }
}
