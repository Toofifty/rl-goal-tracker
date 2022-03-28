package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.google.inject.Guice;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;

import java.awt.image.BufferedImage;

public class ItemTask extends Task
{
    @Setter
    private int amount;

    @Setter
    private ItemComposition item;

    public ItemTask(Goal goal)
    {
        super(goal);
    }

    @Override
    public Boolean check(Client client)
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
        ItemManager itemManager = Guice.createInjector().getInstance(
            ItemManager.class);
        return itemManager.getImage(item.getId());
    }
}
