package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;

public class ItemTask extends Task
{
    private final Client client;
    private final ItemManager itemManager;

    @Setter
    private int quantity;

    @Setter
    @Getter
    private int itemId;

    @Setter
    @Getter
    private String itemName;

    public ItemTask(
        Client client, ItemManager itemManager, Goal goal)
    {
        super(goal);
        this.client = client;
        this.itemManager = itemManager;
    }

    @Override
    public boolean check()
    {
        return false;
    }

    @Override
    public String toString()
    {
        if (quantity == 1) {
            return itemName;
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
        return json;
    }

    @Override
    public BufferedImage getIcon()
    {
        if (!client.isClientThread()) {
            return null;
        }

        return itemManager.getImage(itemId);
    }
}
