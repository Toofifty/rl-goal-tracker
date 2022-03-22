package com.toofifty.goaltracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toofifty.goaltracker.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalSerializer {
    public List<Goal> deserialize(String serialized) {
        List<Goal> goals = new ArrayList<>();
        JsonArray json = new JsonParser().parse(serialized).getAsJsonArray();
        json.forEach((item) -> {
            JsonObject obj = item.getAsJsonObject();
            goals.add(Goal.create(obj));
        });

        return goals;
    }

    public String serialize(List<Goal> goals) {
        JsonArray json = new JsonArray();
        goals.forEach((goal -> json.add(goal.serialize())));

        return json.toString();
    }
}
