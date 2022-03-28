package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.api.Quest;

public class QuestTaskFactory extends TaskFactory
{
    @Override
    protected Task createObject(Goal goal, JsonObject json)
    {
        QuestTask task = new QuestTask(goal);
        int questId = json.get("quest_id").getAsInt();
        task.setQuest(getQuestById(questId));
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
