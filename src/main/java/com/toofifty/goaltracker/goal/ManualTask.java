package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import lombok.Setter;
import net.runelite.api.Client;

import java.awt.image.BufferedImage;

public class ManualTask extends Task
{
    @Setter
    private Boolean done = false;

    @Setter
    private String description;

    public ManualTask(Goal goal)
    {
        super(goal);
    }

    public void toggle()
    {
        done = !done;
    }

    @Override
    public Boolean checkSafe(Client client)
    {
        // no reason to safeguard this check
        return done;
    }

    @Override
    public Boolean check(Client client)
    {
        return done;
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
    public BufferedImage getIcon()
    {
        return null;
    }
}
