package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.AbstractGoal;
import com.toofifty.goaltracker.goal.GoalSet;
import com.toofifty.goaltracker.goal.ManualGoal;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GoalListItemPanel extends JPanel {
    private static final ImageIcon CHECK_MARK_ICON;
    private static final ImageIcon CROSS_MARK_ICON;

    static {
        final BufferedImage checkMark = ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/check_mark.png");
        CHECK_MARK_ICON = new ImageIcon(checkMark);

        final BufferedImage crossMark = ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/cross_mark.png");
        CROSS_MARK_ICON = new ImageIcon(crossMark);
    }

    private Runnable update;
    private AbstractGoal goal;
    private GoalSet goalSet;

    GoalListItemPanel(AbstractGoal goal, GoalSet goalSet) {
        super();

        this.goal = goal;
        this.goalSet = goalSet;
        Boolean goalComplete = goal.isComplete();

        setBorder(new EmptyBorder(2, 2, 2, 2));
        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JLabel description = new JLabel(goal.toString());
        if (goalComplete) {
            description.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
        }
//        description.setBorder(new EmptyBorder(0, 4, 0, 0));
        add(description, BorderLayout.CENTER);

        ImageIcon itemIcon = goalComplete ? CHECK_MARK_ICON : CROSS_MARK_ICON;
        if (goal.hasIcon()) {
            itemIcon = new ImageIcon(goal.getIcon());
        }
        JLabel icon = new JLabel(itemIcon);
        add(icon, BorderLayout.WEST);

        if (goal instanceof ManualGoal) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        ((ManualGoal) goal).setDone(!goalComplete);
                        SwingUtilities.invokeLater(() -> update.run());
                    }
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

        JMenuItem removeGoal = new JMenuItem("Remove");
        removeGoal.addActionListener(e -> {
            goalSet.remove(goal);
            update.run();
        });

        JMenuItem moveUp = new JMenuItem("Move up");
        moveUp.addActionListener(e -> {
            goalSet.move(goal, -1);
            update.run();
        });

        JMenuItem moveDown = new JMenuItem("Move down");
        moveDown.addActionListener(e -> {
            goalSet.move(goal, 1);
            update.run();
        });

        JMenuItem moveToTop = new JMenuItem("Move to top");
        moveToTop.addActionListener(e -> {
            goalSet.moveToTop(goal);
            update.run();
        });

        JMenuItem moveToBottom = new JMenuItem("Move to bottom");
        moveToBottom.addActionListener(e -> {
            goalSet.moveToBottom(goal);
            update.run();
        });

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (!goalSet.isFirst(goal)) popupMenu.add(moveUp);
        if (!goalSet.isLast(goal)) popupMenu.add(moveDown);
        if (!goalSet.isFirst(goal)) popupMenu.add(moveToTop);
        if (!goalSet.isLast(goal)) popupMenu.add(moveToBottom);
        popupMenu.add(removeGoal);

        setComponentPopupMenu(popupMenu);
    }

    public void onUpdate(Runnable update) {
        this.update = update;
    }

}
