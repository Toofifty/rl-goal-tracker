package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.GoalSet;

public class GoalSetFactory {
    private AbstractGoalFactory getGoalFactory(JsonObject object) {
        switch (object.get("type").getAsString()) {
            case "manual": return new ManualGoalFactory();
            default: throw new IllegalStateException("Type required to load goals");
        }
    }

    public GoalSet create(JsonObject json) {
        GoalSet goalSet = new GoalSet();
        goalSet.setDescription(json.get("description").getAsString());
        goalSet.setDisplayOrder(json.get("display_order").getAsInt());

        json.get("items").getAsJsonArray().forEach(item -> {
            JsonObject obj = item.getAsJsonObject();
            goalSet.add(this.getGoalFactory(obj).create(obj));
        });

        return goalSet;
    }
}
