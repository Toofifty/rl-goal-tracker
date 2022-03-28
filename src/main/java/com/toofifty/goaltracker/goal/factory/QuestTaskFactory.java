package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.api.Quest;

public class QuestTaskFactory extends TaskFactory
{
    public QuestTaskFactory(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected Task createObjectFromJson(JsonObject json)
    {
        return create(getQuestById(json.get("quest_id").getAsInt()));
    }

    public QuestTask create(Quest quest)
    {
        QuestTask task = new QuestTask(plugin.getClient(), goal);
        task.setQuest(quest);
        return task;
    }

    private Quest getQuestById(int id)
    {
        for (Quest quest : Quest.values()) {
            if (quest.getId() == id) {
                return quest;
            }
        }
        throw new IllegalStateException("Quest not found");
    }
}
