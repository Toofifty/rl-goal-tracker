package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;

public class ManualTaskFactory extends TaskFactory
{
    public ManualTaskFactory(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected ManualTask createObjectFromJson(JsonObject json)
    {
        return create(json.get("description").getAsString(),
            json.get("done").getAsBoolean());
    }

    public ManualTask create(String description, boolean done)
    {
        ManualTask task = new ManualTask(goal);
        task.setDescription(description);
        task.setDone(done);
        return task;
    }
}
