package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.ManualGoal;

public class ManualGoalFactory extends AbstractGoalFactory {
    @Override
    public ManualGoal create(JsonObject json) {
        ManualGoal goal = new ManualGoal();
        goal.setDescription(json.get("description").getAsString());
        goal.setDone(json.get("done").getAsBoolean());
        return goal;
    }
}
