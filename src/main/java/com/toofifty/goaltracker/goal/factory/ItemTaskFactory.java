package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.google.inject.Guice;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ItemTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;

public class ItemTaskFactory extends TaskFactory
{
    @Override
    protected Task createObject(
        Goal goal, JsonObject json)
    {
        ItemTask task = new ItemTask(goal);
        ItemComposition item = Guice.createInjector().getInstance(
            ItemManager.class).getItemComposition(
            json.get("item_id").getAsInt());
        task.setItem(item);
        task.setAmount(json.get("amount").getAsInt());
        return null;
    }
}
