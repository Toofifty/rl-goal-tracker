package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.TaskFactory;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class Task
{
    @Getter
    protected TaskStatus result = TaskStatus.NOT_STARTED;

    @Accessors(fluent = true)
    @Setter
    @Getter
    private boolean hasBeenNotified = false;

    @Override
    abstract public String toString();

    public JsonObject serialize()
    {
        JsonObject json = new JsonObject();
        json.addProperty("type", getType().getName());
        json.addProperty("previous_result", result.getName());
        json.addProperty("has_been_notified", hasBeenNotified);
        return addSerializedProperties(json);
    }

    abstract public TaskType getType();

    abstract protected JsonObject addSerializedProperties(JsonObject json);

    abstract public Class<? extends TaskFactory<? extends Task>> getFactoryClass();

    public TaskStatus setResult(TaskStatus result)
    {
        return this.result = result;
    }
}
