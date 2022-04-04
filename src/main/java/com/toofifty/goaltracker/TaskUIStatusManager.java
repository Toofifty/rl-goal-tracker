package com.toofifty.goaltracker;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.Task;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import javax.swing.SwingUtilities;

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
                System.out.println("Missing task refresher for " + task.hashCode());
                for (Task key : taskRefreshers.keySet()) {
                    System.out.println("Has " + key.hashCode() + ". is same: " + (task.equals(key) ? "ye" : "no"));
                }

            }
//            if (goalRefreshers.containsKey(task.getGoal())) {
//                goalRefreshers.get(task.getGoal()).run();
//            } else {
//                System.out.println(
//                    "Missing goal refresher for " + task.toString());
//            }
        });
    }
}
