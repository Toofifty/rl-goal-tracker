package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.TaskUIStatusManager;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

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
        TaskStatus status = task.check();

        titleLabel.setText(task.toString());
        titleLabel.setForeground(getForegroundColor(status));

        if (status.isCompleted()) {
            iconLabel.setIcon(CHECK_MARK_ICON);

        } else if (task.hasIcon()) {
            iconLabel.setIcon(
                new ImageIcon(task.getIcon().getScaledInstance(16, 16, 32)));

        } else {
            iconLabel.setIcon(CROSS_MARK_ICON);

        }

        revalidate();
    }

    private Color getForegroundColor(TaskStatus status)
    {
        switch (status) {
            case IN_PROGRESS:
                return ColorScheme.PROGRESS_INPROGRESS_COLOR;
            case COMPLETED:
                return ColorScheme.PROGRESS_COMPLETE_COLOR;
        }
        return ColorScheme.PROGRESS_ERROR_COLOR;
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
