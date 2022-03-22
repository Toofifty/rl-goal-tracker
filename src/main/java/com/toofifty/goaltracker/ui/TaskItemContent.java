package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TaskItemContent extends JPanel {
    private static final ImageIcon CHECK_MARK_ICON;
    private static final ImageIcon CROSS_MARK_ICON;

    static {
        final BufferedImage checkMark = ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/check_mark.png");
        CHECK_MARK_ICON = new ImageIcon(checkMark);

        final BufferedImage crossMark = ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/cross_mark.png");
        CROSS_MARK_ICON = new ImageIcon(crossMark);
    }

    TaskItemContent(Task task) {
        super(new BorderLayout());

        Boolean taskComplete = task.isComplete();

        JLabel title = new JLabel(task.toString());
        if (taskComplete) {
            title.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
        }
        title.setPreferredSize(new Dimension(0, 24));
        add(title, BorderLayout.CENTER);

        ImageIcon itemIcon = taskComplete ? CHECK_MARK_ICON : CROSS_MARK_ICON;
        if (task.hasIcon()) {
            itemIcon = new ImageIcon(task.getIcon());
        }
        JLabel icon = new JLabel(itemIcon);
        JPanel iconWrapper = new JPanel(new BorderLayout());
        iconWrapper.add(icon, BorderLayout.NORTH);
        add(iconWrapper, BorderLayout.WEST);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        for (Component component : getComponents()) {
            component.setBackground(bg);
        }
    }
}
