package com.toofifty.goaltracker;

import com.google.inject.Provides;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.Task;
import java.util.Map;
import java.util.WeakHashMap;
import javax.swing.SwingUtilities;
import lombok.Getter;

public class TaskUIStatusManager
{
    private static TaskUIStatusManager instance;

    @Getter
    private final Map<Task, Runnable> taskRefreshers = new WeakHashMap<>();

    @Getter
    private final Map<Goal, Runnable> goalRefreshers = new WeakHashMap<>();

    public static TaskUIStatusManager getInstance()
    {
        if (instance == null) {
            instance = new TaskUIStatusManager();
        }
        return instance;
    }

    public void addRefresher(Task task, Runnable refresher)
    {
        getTaskRefreshers().put(task, refresher);
    }

    public void addRefresher(Goal goal, Runnable refresher)
    {
        getGoalRefreshers().put(goal, refresher);
    }

    public void refresh(Task task)
    {
        SwingUtilities.invokeLater(() -> {
            if (taskRefreshers.containsKey(task)) {
                taskRefreshers.get(task).run();
            } else {
                System.out.println(
                    "Missing task refresher for " + task.toString());
            }
            if (goalRefreshers.containsKey(task.getGoal())) {
                goalRefreshers.get(task.getGoal()).run();
            } else {
                System.out.println(
                    "Missing goal refresher for " + task.toString());
            }
        });
    }

    @Provides
    public TaskUIStatusManager getTaskUIStatusManager()
    {
        return TaskUIStatusManager.getInstance();
    }
}
