package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.ManualTask;

public class ManualTaskFactory extends TaskFactory {
    @Override
    public ManualTask create(JsonObject json) {
        ManualTask goal = new ManualTask();
        goal.setDescription(json.get("description").getAsString());
        goal.setDone(json.get("done").getAsBoolean());
        return goal;
    }
}
