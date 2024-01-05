package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.awt.image.BufferedImage;

@Setter
@Getter
@SuperBuilder
public class ItemTask extends Task
{
    private transient BufferedImage cachedIcon;
    private int quantity;
    @Builder.Default
    private int acquired = 0;
    private int itemId;
    private String itemName;

    @Override
    public String toString()
    {
        if (quantity == 1) {
            return itemName;
        }

        if (acquired > 0 && acquired < quantity) {
            return String.format("%d/%d x %s", acquired, quantity, itemName);
        }

        return String.format("%d x %s", quantity, itemName);
    }

    @Override
    public TaskType getType()
    {
        return TaskType.ITEM;
    }
}
