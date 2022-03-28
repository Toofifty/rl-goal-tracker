package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.TaskUIStatusManager;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TaskItemContent extends JPanel implements Refreshable
{
    private static final ImageIcon CHECK_MARK_ICON;
    private static final ImageIcon CROSS_MARK_ICON;

    static {
        final BufferedImage checkMark = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/check_mark.png");
        CHECK_MARK_ICON = new ImageIcon(checkMark);

        final BufferedImage crossMark = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/cross_mark.png");
        CROSS_MARK_ICON = new ImageIcon(crossMark);
    }

    private final GoalTrackerPlugin plugin;
    private final Task task;
    private final JLabel titleLabel = new JLabel();
    private final JLabel iconLabel = new JLabel(CROSS_MARK_ICON);

    TaskItemContent(GoalTrackerPlugin plugin, Task task)
    {
        super(new BorderLayout());
        this.plugin = plugin;
        this.task = task;

        titleLabel.setPreferredSize(new Dimension(0, 24));
        add(titleLabel, BorderLayout.CENTER);

        JPanel iconWrapper = new JPanel(new BorderLayout());
        iconWrapper.setBorder(new EmptyBorder(4, 0, 0, 4));
        iconWrapper.add(iconLabel, BorderLayout.NORTH);
        add(iconWrapper, BorderLayout.WEST);

        TaskUIStatusManager.getInstance().addRefresher(task, this::refresh);
    }

    @Override
    public void refresh()
    {
        Boolean taskComplete = task.checkSafe(plugin.getClient());
        System.out.println(
            "Refresh TaskItemContent, task: " + task.toString() + " complete: " + taskComplete);

        titleLabel.setText(task.toString());
        titleLabel.setForeground(
            taskComplete ? ColorScheme.PROGRESS_COMPLETE_COLOR
                         : ColorScheme.LIGHT_GRAY_COLOR);

        if (taskComplete) {
            titleLabel.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
            iconLabel.setIcon(CHECK_MARK_ICON);
            return;
        }

        titleLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);

        if (task.hasIcon()) {
            iconLabel.setIcon(
                new ImageIcon(task.getIcon().getScaledInstance(16, 16, 32)));
            return;
        }

        iconLabel.setIcon(CROSS_MARK_ICON);

        revalidate();
    }

    @Override
    public void setBackground(Color bg)
    {
        super.setBackground(bg);
        for (Component component : getComponents()) {
            component.setBackground(bg);
        }
    }
}
