package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskStatus;

abstract public class TaskFactory<T extends Task>
{
    public T create(JsonObject json)
    {
        T task = create();

        task.setResult(TaskStatus.valueOf(json.get("previous_result").getAsString().toUpperCase()));
        return task;
    }

    public abstract T create();
}
