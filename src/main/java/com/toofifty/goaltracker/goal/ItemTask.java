package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.ItemCache;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;

public class ItemTask extends Task
{
    private final Client client;
    private final ItemManager itemManager;
    private final ItemCache itemCache;

    private BufferedImage cachedIcon;

    @Setter
    private int quantity;

    @Setter
    private int acquired = 0;

    @Setter
    @Getter
    private int itemId;

    @Setter
    @Getter
    private String itemName;

    public ItemTask(
        Client client, ItemManager itemManager, ItemCache itemCache, Goal goal)
    {
        super(goal);
        this.client = client;
        this.itemManager = itemManager;
        this.itemCache = itemCache;
    }

    @Override
    public TaskStatus check()
    {
        acquired = Math.min(itemCache.getTotalQuantity(itemId), quantity);

        return acquired >= quantity ? TaskStatus.COMPLETED
            : (acquired > 0 ? TaskStatus.IN_PROGRESS : TaskStatus.NOT_STARTED);
    }

    @Override
    public String toString()
    {
        if (quantity == 1) {
            return itemName;
        }

        if (acquired > 0 && acquired < quantity) {
            return String.format("%,d", acquired) + "/" + String.format("%,d", quantity) + " x " + itemName;
        }

        return String.format("%,d", quantity) + " x " + itemName;
    }

    @Override
    public TaskType getType()
    {
        return TaskType.ITEM;
    }

    @Override
    protected JsonObject addSerializedProperties(
        JsonObject json)
    {
        json.addProperty("item_id", itemId);
        json.addProperty("item_name", itemName);
        json.addProperty("quantity", quantity);
        json.addProperty("acquired", acquired);
        return json;
    }

    @Override
    public BufferedImage getIcon()
    {
        if (cachedIcon != null) {
            return cachedIcon;
        }

        if (!client.isClientThread()) {
            return null;
        }

        return cachedIcon = itemManager.getImage(itemId);
    }
}
