package com.toofifty.goaltracker;

import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Singleton
public class TaskUIStatusManager
{
    private final Map<Task, Runnable> taskRefreshers = new HashMap<>();
    private final Map<Goal, Runnable> goalRefreshers = new HashMap<>();

    public void addRefresher(Task task, Runnable refresher)
    {
        taskRefreshers.put(task, refresher);
    }

    public void addRefresher(Goal goal, Runnable refresher)
    {
        goalRefreshers.put(goal, refresher);
    }

    public void refresh(Task task)
    {
        SwingUtilities.invokeLater(() -> {
            if (taskRefreshers.containsKey(task)) {
                taskRefreshers.get(task).run();
            } else {
                log.debug("Missing task refresher for " + task.hashCode());
                for (Task key : taskRefreshers.keySet()) {
                    log.debug("Has " + key.hashCode() + ". is same: " + (task.equals(key) ? "ye" : "no"));
                }
            }
        });
    }

    public void refresh(Goal goal)
    {
        SwingUtilities.invokeLater(() -> {
            if (goalRefreshers.containsKey(goal)) {
                goalRefreshers.get(goal).run();
            } else {
                log.debug("Missing goal refresher for " + goal.hashCode());
            }
        });
    }
}
