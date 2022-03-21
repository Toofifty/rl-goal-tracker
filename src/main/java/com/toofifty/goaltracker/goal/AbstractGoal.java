package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;

import java.awt.image.BufferedImage;

public abstract class AbstractGoal {
    @Override
    abstract public String toString();

    abstract public Boolean isComplete();

    abstract public BufferedImage getIcon();

    abstract public JsonObject serialize();

    public Boolean hasIcon() {
        return this.getIcon() != null;
    }
}
