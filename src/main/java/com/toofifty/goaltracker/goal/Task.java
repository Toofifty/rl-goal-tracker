package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;

public abstract class Task
{
    @Setter
    @Getter
    protected TaskStatus result = TaskStatus.NOT_STARTED;

    @Getter
    private Goal goal;
    private boolean hasBeenNotified = false;

    public Task(Goal goal)
    {
        this.goal = goal;
    }

    public void hasBeenNotified(Boolean hasBeenNotified)
    {
        this.hasBeenNotified = hasBeenNotified;
    }

    public boolean hasBeenNotified()
    {
        return hasBeenNotified;
    }

    public TaskStatus check()
    {
        return result;
    }

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

    public boolean hasIcon()
    {
        return this.getIcon() != null;
    }

    abstract public BufferedImage getIcon();

    public void save()
    {
        goal.save();
    }

}
