package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.QuestTaskFactory;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Quest;

public class QuestTask extends Task
{
    @Getter
    @Setter
    private Quest quest;

    @Override
    public String toString()
    {
        return quest.getName();
    }

    @Override
    public TaskType getType()
    {
        return TaskType.QUEST;
    }

    @Override
    protected JsonObject addSerializedProperties(JsonObject json)
    {
        json.addProperty("quest_id", quest.getId());
        return json;
    }

    @Override
    public Class<QuestTaskFactory> getFactoryClass()
    {
        return QuestTaskFactory.class;
    }
}
