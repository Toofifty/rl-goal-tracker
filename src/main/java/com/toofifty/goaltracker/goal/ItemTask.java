package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.ItemTaskFactory;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;

public class ItemTask extends Task
{
    @Getter
    private BufferedImage cachedIcon;

    @Setter
    @Getter
    private int quantity;

    @Setter
    @Getter
    private int acquired = 0;

    @Setter
    @Getter
    private int itemId;

    @Setter
    @Getter
    private String itemName;

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
    public Class<ItemTaskFactory> getFactoryClass()
    {
        return ItemTaskFactory.class;
    }

    public BufferedImage setCachedIcon(BufferedImage cachedIcon)
    {
        return this.cachedIcon = cachedIcon;
    }
}
