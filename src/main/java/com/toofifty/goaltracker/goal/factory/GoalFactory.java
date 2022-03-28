package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalManager;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.TaskType;

public class GoalFactory
{
    private final GoalTrackerPlugin plugin;

    public GoalFactory(GoalTrackerPlugin plugin)
    {
        this.plugin = plugin;
    }

    public Goal create(GoalManager goalManager, JsonObject json) throws
        Exception
    {
        Goal goal = new Goal(goalManager);
        goal.setDescription(json.get("description").getAsString());
        goal.setDisplayOrder(json.get("display_order").getAsInt());

        for (JsonElement item : json.get("items").getAsJsonArray()) {
            JsonObject obj = item.getAsJsonObject();
            Class<?> factoryClass = TaskType.fromString(
                obj.get("type").getAsString()).getFactory();

            TaskFactory factory = (TaskFactory) factoryClass
                .getDeclaredConstructor(GoalTrackerPlugin.class, Goal.class)
                .newInstance(plugin, goal);

            goal.add(factory.createFromJson(obj));
        }

        return goal;
    }
}
