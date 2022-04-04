package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.ItemTask;

public class ItemTaskFactory extends TaskFactory<ItemTask>
{
    @Override
    public ItemTask create(JsonObject json)
    {
        return create(
            json.get("item_id").getAsInt(),
            json.get("item_name").getAsString(),
            json.get("quantity").getAsInt(),
            json.get("acquired").getAsInt()
        );
    }

    @Override
    public ItemTask create()
    {
        return new ItemTask();
    }

    public ItemTask create(int itemId, String itemName, int quantity, int acquired)
    {
        ItemTask task = create();
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
