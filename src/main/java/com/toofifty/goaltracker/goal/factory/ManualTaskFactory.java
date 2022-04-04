package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.ManualTask;

public class ManualTaskFactory extends TaskFactory<ManualTask>
{
    @Override
    public ManualTask create(JsonObject json)
    {
        return create(
            json.get("description").getAsString(),
            json.get("done").getAsBoolean()
        );
    }

    @Override
    public ManualTask create()
    {
        return new ManualTask();
    }

    public ManualTask create(String description, boolean done)
    {
        ManualTask task = create();
        task.setDescription(description);
        task.setDone(done);
        return task;
    }

    public ManualTask create(String description)
    {
        return create(description, false);
    }
}
