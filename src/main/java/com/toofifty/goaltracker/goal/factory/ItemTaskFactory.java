package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ItemTask;

public class ItemTaskFactory extends TaskFactory
{
    public ItemTaskFactory(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected ItemTask createObjectFromJson(JsonObject json)
    {
        return create(
            json.get("item_id").getAsInt(), json.get("item_name").getAsString(),
            json.get("quantity").getAsInt(), json.get("acquired").getAsInt());
    }

    public ItemTask create(int itemId, String itemName, int quantity, int acquired)
    {

        ItemTask task = new ItemTask(plugin.getClient(), plugin.getItemManager(), plugin.getItemCache(), goal);

        task.setItemId(itemId);
        task.setItemName(itemName);
        task.setQuantity(quantity);
        task.setAcquired(acquired);
        return task;
    }

    public ItemTask create(int itemId, String itemName, int quantity)
    {
        return create(itemId, itemName, quantity, 0);
    }
}
