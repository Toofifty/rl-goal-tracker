package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ItemTask;
import com.toofifty.goaltracker.ui.ComboBox;
import net.runelite.api.ItemComposition;

public class ItemTaskInput extends TaskInput
{
    private final Goal goal;
    private final ComboBox<ItemComposition> itemField;

    public ItemTaskInput(Goal goal)
    {
        super("Item");
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
        //        itemField = new ComboBox<>(allItems);

        //        getInputRow().add(itemField, BorderLayout.CENTER);
    }

    @Override
    protected void onSubmit()
    {
        ItemTask task = new ItemTask(goal);
        task.setItem((ItemComposition) itemField.getSelectedItem());
        task.setAmount(1);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        itemField.setSelectedIndex(0);
    }
}
