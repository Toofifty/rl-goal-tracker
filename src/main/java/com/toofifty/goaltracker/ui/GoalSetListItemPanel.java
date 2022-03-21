package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.GoalSet;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GoalSetListItemPanel extends JPanel {
    GoalSetListItemPanel(GoalSet goalSet, Runnable listener) {
        super();

        setBorder(new EmptyBorder(6, 6, 6, 6));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new BorderLayout());

        JLabel title = new JLabel();
        title.setText(goalSet.getDescription());
        title.setForeground(
                goalSet.isComplete()
                        ? ColorScheme.PROGRESS_COMPLETE_COLOR
                        : goalSet.isInProgress()
                        ? ColorScheme.PROGRESS_INPROGRESS_COLOR
                        : ColorScheme.PROGRESS_ERROR_COLOR
        );
        add(title, BorderLayout.WEST);

        JLabel progress = new JLabel();
        progress.setText(goalSet.getComplete().size() + "/" + goalSet.getGoals().size());
        progress.setForeground(
                goalSet.isComplete()
                        ? ColorScheme.PROGRESS_COMPLETE_COLOR
                        : goalSet.isInProgress()
                        ? ColorScheme.PROGRESS_INPROGRESS_COLOR
                        : ColorScheme.PROGRESS_ERROR_COLOR
        );
        add(progress, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                listener.run();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorScheme.DARK_GRAY_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(ColorScheme.DARK_GRAY_COLOR);
            }
        });
    }
}
