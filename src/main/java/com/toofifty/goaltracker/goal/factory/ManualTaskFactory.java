package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;

public class ManualTaskFactory extends TaskFactory
{
    @Override
    protected ManualTask createObject(Goal goal, JsonObject json)
    {
        ManualTask task = new ManualTask(goal);
        task.setDescription(json.get("description").getAsString());
        task.setDone(json.get("done").getAsBoolean());
        return task;
    }
}
