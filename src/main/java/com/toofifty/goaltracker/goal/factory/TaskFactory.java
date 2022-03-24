package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.Task;

abstract public class TaskFactory
{
    final public Task create(Goal goal, JsonObject json)
    {
        Task task = createObject(goal, json);
        task.setPreviousResult(json.get("previous_result").getAsBoolean());
        return task;
    }

    abstract protected Task createObject(Goal goal, JsonObject json);
}
