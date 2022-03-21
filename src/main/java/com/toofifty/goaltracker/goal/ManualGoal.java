package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import lombok.Setter;

import java.awt.image.BufferedImage;

public class ManualGoal extends AbstractGoal {
    @Setter
    private Boolean done = false;

    @Setter
    private String description;

    @Override
    public String toString() {
        return description;
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
    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", "manual");
        json.addProperty("done", done);
        json.addProperty("description", description);
        return json;
    }
}
