package com.toofifty.goaltracker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Item;

@Singleton
public class ItemCache
{
    private Map<Integer, Item[]> inventories;
    private Map<Integer, Integer> itemTotals;

    @Inject
    private GoalTrackerConfig config;

    @Inject
    private Gson gson;

    public void save()
    {
        config.goalTrackerItemCache(gson.toJson(inventories));
    }

    public void load()
    {
        Map<Integer, Item[]> savedInventories = gson
            .fromJson(config.goalTrackerItemCache(), new TypeToken<Map<Integer, Item[]>>()
            {
            }.getType());

        if (savedInventories != null) {
            inventories = savedInventories;
        } else {
            inventories = new HashMap<>();
        }

        calculateItemTotals();
    }

    public void calculateItemTotals()
    {
        itemTotals = new HashMap<>();
        for (int inventory : inventories.keySet()) {
            for (Item item : inventories.get(inventory)) {
                itemTotals.put(item.getId(), itemTotals.getOrDefault(item.getId(), 0) + item.getQuantity());
            }
        }
    }

    public void update(int inventoryID, Item[] contents)
    {
        inventories.put(inventoryID, contents);

        calculateItemTotals();
    }

    public int getTotalQuantity(int itemId)
    {
        return itemTotals.getOrDefault(itemId, 0);
    }
}
