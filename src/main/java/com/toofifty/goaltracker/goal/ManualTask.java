package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.ManualTaskFactory;
import lombok.Getter;
import lombok.Setter;

public class ManualTask extends Task
{
    @Getter
    @Setter
    private boolean done = false;

    @Getter
    @Setter
    private String description;

    public void toggle()
    {
        done = !done;
    }

    @Override
    public String toString()
    {
        return description;
    }

    @Override
    public TaskType getType()
    {
        return TaskType.MANUAL;
    }

    @Override
    public JsonObject addSerializedProperties(JsonObject json)
    {
        json.addProperty("done", done);
        json.addProperty("description", description);
        return json;
    }

    @Override
    public Class<ManualTaskFactory> getFactoryClass()
    {
        return ManualTaskFactory.class;
    }
}
