package com.toofifty.goaltracker;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskType;
import com.toofifty.goaltracker.goal.factory.GoalFactory;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GoalManager implements ReorderableList<Goal>
{
    private List<Goal> goals = new ArrayList<>();

    @Inject
    private GoalTrackerConfig config;

    @Inject
    private GoalSerializer goalSerializer;

    @Inject
    private GoalFactory goalFactory;

    public void save()
    {
        config.goalTrackerData(goalSerializer.serialize(goals));

        System.out.println("Saved " + goals.size() + " goals");
    }

    public Goal createGoal()
    {
        Goal goal = goalFactory.create();
        add(goal);
        return goal;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getAllIncompleteTasksOfType(TaskType type)
    {
        List<T> tasks = new ArrayList<>();
        for (Goal goal : goals) {
            for (Task task : goal.getAll()) {
                if (!task.getResult().isCompleted() && task.getType() == type) {
                    tasks.add((T) task);
                }
            }
        }
        return tasks;
    }

    @Override
    public List<Goal> getAll()
    {
        return goals;
    }

    @Override
    public void add(Goal goal)
    {
        goals.add(goal);
    }

    @Override
    public void remove(Goal goal)
    {
        goals.remove(goal);
    }

    @Override
    public void move(Goal goal, int offset)
    {
        int index = goals.indexOf(goal);
        goals.remove(goal);
        goals.add(index + offset, goal);
    }

    @Override
    public void moveToTop(Goal goal)
    {
        goals.remove(goal);
        goals.add(0, goal);
    }

    @Override
    public void moveToBottom(Goal goal)
    {
        goals.remove(goal);
        goals.add(goal);
    }

    @Override
    public Boolean isFirst(Goal goal)
    {
        return goals.get(0) == goal;
    }

    @Override
    public Boolean isLast(Goal goal)
    {
        return goals.get(goals.size() - 1) == goal;
    }

    public void load()
    {
        try {
            goals = goalSerializer.deserialize(config.goalTrackerData());
        } catch (Exception e) {
            goals = new ArrayList<>();
        }

        System.out.println("Loaded " + goals.size() + " goals");
    }
}
