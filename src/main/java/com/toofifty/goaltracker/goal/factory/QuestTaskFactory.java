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
        task.setQuest(Quest.valueOf(json.get("quest_name").getAsString()));
        return task;
    }
}
