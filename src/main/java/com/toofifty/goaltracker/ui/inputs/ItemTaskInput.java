package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ItemTask;
import com.toofifty.goaltracker.goal.factory.ItemTaskFactory;
import com.toofifty.goaltracker.ui.ComboBox;
import net.runelite.api.ItemComposition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ItemTaskInput extends TaskInput
{
    private final Goal goal;
    private final ComboBox<ItemComposition> itemField;

    public ItemTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, "Item");
        this.goal = goal;

        //        ItemManager itemManager = Guice.createInjector().getInstance(
        //            ItemManager.class);
        //
        //        List<ItemComposition> allItems = new ArrayList<>();
        //        for (int i = 0; i < 30000; i++) {
        //            ItemComposition item = itemManager.getItemComposition(i);
        //            if (item != null) {
        //                allItems.add(item);
        //            }
        //        }
        //
        //        allItems.sort(Comparator.comparing(ItemComposition::getName));
        List<ItemComposition> allItems = new ArrayList<>();
        itemField = new ComboBox<>(allItems);

        getInputRow().add(itemField, BorderLayout.CENTER);
    }

    @Override
    protected void onSubmit()
    {
        ItemTask task = new ItemTaskFactory(plugin, goal).create(
            (int) itemField.getSelectedItem(), 1);
        goal.add(task);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        itemField.setSelectedIndex(0);
    }
}
