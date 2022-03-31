package com.toofifty.goaltracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.factory.GoalFactory;
import java.util.ArrayList;
import java.util.List;

public class GoalSerializer
{
    private final GoalFactory goalFactory;

    GoalSerializer(GoalTrackerPlugin plugin)
    {
        goalFactory = new GoalFactory(plugin);
    }

    public List<Goal> deserialize(
        GoalManager goalManager, String serialized) throws Exception
    {
        List<Goal> goals = new ArrayList<>();
        JsonArray json = new JsonParser().parse(serialized).getAsJsonArray();

        for (JsonElement item : json) {
            JsonObject obj = item.getAsJsonObject();
            goals.add(goalFactory.create(goalManager, obj));
        }

        return goals;
    }

    public String serialize(List<Goal> goals)
    {
        JsonArray json = new JsonArray();
        goals.forEach((goal -> json.add(goal.serialize())));

        return json.toString();
    }
}
