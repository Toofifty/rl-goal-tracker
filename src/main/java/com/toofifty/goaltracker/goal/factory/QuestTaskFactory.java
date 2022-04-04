package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.QuestTask;
import net.runelite.api.Quest;

public class QuestTaskFactory extends TaskFactory<QuestTask>
{
    @Override
    public QuestTask create(JsonObject json)
    {
        return create(getQuestById(json.get("quest_id").getAsInt()));
    }

    @Override
    public QuestTask create()
    {
        return new QuestTask();
    }

    public QuestTask create(Quest quest)
    {
        QuestTask task = create();
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
