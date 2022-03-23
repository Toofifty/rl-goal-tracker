package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class ManualTask extends Task {
    @Setter
    private Boolean done = false;

    @Setter
    private String description;

    public ManualTask(Goal goal) {
        super(goal);
    }

    public void toggle() {
        done = !done;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public TaskType getType() {
        return TaskType.MANUAL;
    }

    @Override
    public Boolean isComplete() {
        return done;
    }

    @Override
    public BufferedImage getIcon() {
        return null;
    }

    @Override
    public JsonObject addSerializedProperties(JsonObject json) {
        json.addProperty("done", done);
        json.addProperty("description", description);
        return json;
    }
}
