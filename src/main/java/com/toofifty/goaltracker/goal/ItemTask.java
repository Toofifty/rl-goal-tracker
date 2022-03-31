package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;

public class ItemTask extends Task
{
    private final Client client;
    private final ItemManager itemManager;

    @Setter
    private int amount;

    @Setter
    private ItemComposition item;

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
        return String.format("%,d", amount) + " x " + item.getName();
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
        json.addProperty("item_id", item.getId());
        json.addProperty("amount", amount);
        return json;
    }

    @Override
    public BufferedImage getIcon()
    {
        return itemManager.getImage(item.getId());
    }
}
