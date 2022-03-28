package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ItemTask;
import net.runelite.api.ItemComposition;

public class ItemTaskFactory extends TaskFactory
{
    public ItemTaskFactory(
        GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected ItemTask createObjectFromJson(JsonObject json)
    {
        return create(
            json.get("item_id").getAsInt(), json.get("amount").getAsInt());
    }

    public ItemTask create(int itemId, int amount)
    {
        ItemTask task = new ItemTask(
            plugin.getClient(), plugin.getItemManager(), goal);
        ItemComposition item = plugin.getItemManager().getItemComposition(
            itemId);
        task.setItem(item);
        task.setAmount(amount);
        return task;
    }
}
