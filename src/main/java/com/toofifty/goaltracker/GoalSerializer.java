package com.toofifty.goaltracker;

import com.google.gson.*;
import com.toofifty.goaltracker.goal.GoalSet;
import com.toofifty.goaltracker.goal.factory.GoalSetFactory;

import java.util.ArrayList;
import java.util.List;

public class GoalSerializer {
    private GoalSetFactory goalSetFactory;

    GoalSerializer() {
        goalSetFactory = new GoalSetFactory();
    }

    public List<GoalSet> deserialize(String serialized) {
        List<GoalSet> goalSets = new ArrayList<>();
        JsonArray json = new JsonParser().parse(serialized).getAsJsonArray();
        json.forEach((item) -> {
            JsonObject obj = item.getAsJsonObject();
            goalSets.add(goalSetFactory.create(obj));
        });

        return goalSets;
    }

    public String serialize(List<GoalSet> goalSets) {
        JsonArray json = new JsonArray();
        goalSets.forEach((goalSet -> json.add(goalSet.serialize())));

        return json.toString();
    }
}
