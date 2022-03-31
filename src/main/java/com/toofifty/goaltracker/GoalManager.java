package com.toofifty.goaltracker;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskType;
import java.util.ArrayList;
import java.util.List;

public class GoalManager implements ReorderableList<Goal>
{
    private List<Goal> goals;

    private GoalTrackerConfig config;

    private GoalSerializer goalSerializer;

    public GoalManager(GoalTrackerPlugin plugin)
    {
        config = plugin.getConfig();
        goalSerializer = new GoalSerializer(plugin);
    }

    public void save()
    {
        config.goalTrackerData(goalSerializer.serialize(goals));

        System.out.println("Saved " + goals.size() + " goals");
    }

    public void load()
    {
        try {
            goals = goalSerializer.deserialize(this, config.goalTrackerData());
        } catch (Exception e) {
            goals = new ArrayList<>();
        }

        System.out.println("Loaded " + goals.size() + " goals");
    }

    public Goal createGoal()
    {
        Goal goal = new Goal(this);
        add(goal);
        return goal;
    }

    public List<SkillLevelTask> getAllSkillTasks()
    {
        List<SkillLevelTask> tasks = new ArrayList<>();
        for (Goal goal : goals) {
            for (Task task : goal.getAll()) {
                if (task instanceof SkillLevelTask) {
                    tasks.add((SkillLevelTask) task);
                }
            }
        }
        return tasks;
    }

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
        return this.goals;
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
}
