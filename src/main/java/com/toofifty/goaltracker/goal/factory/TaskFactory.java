package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskStatus;

abstract public class TaskFactory
{
    protected final GoalTrackerPlugin plugin;
    protected final Goal goal;

    public TaskFactory(GoalTrackerPlugin plugin, Goal goal)
    {
        this.plugin = plugin;
        this.goal = goal;
    }

    public Task createFromJson(JsonObject json)
    {
        Task task = createObjectFromJson(json);
        task.setResult(TaskStatus.valueOf(json.get("previous_result").getAsString().toUpperCase()));
        return task;
    }

    abstract protected Task createObjectFromJson(JsonObject json);
}
