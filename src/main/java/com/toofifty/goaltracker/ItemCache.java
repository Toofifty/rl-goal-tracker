package com.toofifty.goaltracker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.Item;
import net.runelite.client.game.ItemManager;

@Singleton
public class ItemCache
{
    private Map<Integer, Item[]> inventories;
    private Map<Integer, Integer> itemTotals;

    // mapping of itemIds to their noted counterparts, and vice versa
    // needs to be persisted to show correct quantities when not logged in
    private Map<Integer, Integer> itemNoteMap;

    @Inject
    private GoalTrackerConfig config;

    @Inject
    private Gson gson;

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    public void save()
    {
        config.goalTrackerItemCache(gson.toJson(inventories));
        config.goalTrackerItemNoteMapCache(gson.toJson(itemNoteMap));
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

        Map<Integer, Integer> savedItemNoteMap = gson
            .fromJson(config.goalTrackerItemNoteMapCache(), new TypeToken<Map<Integer, Integer>>()
            {
            }.getType());

        if (savedItemNoteMap != null) {
            itemNoteMap = savedItemNoteMap;
        } else {
            itemNoteMap = new HashMap<>();
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
        int notedItemId = getNotedId(itemId);

        if (notedItemId != -1) {
            return itemTotals.getOrDefault(itemId, 0) + itemTotals.getOrDefault(notedItemId, 0);
        }

        return itemTotals.getOrDefault(itemId, 0);
    }

    private int getNotedId(int itemId)
    {
        if (client.isClientThread() && !itemNoteMap.containsKey(itemId)) {
            itemNoteMap.put(itemId, itemManager.getItemComposition(itemId).getLinkedNoteId());
        }
        return itemNoteMap.getOrDefault(itemId, -1);
    }
}
