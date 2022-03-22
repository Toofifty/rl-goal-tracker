package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Task;

abstract public class TaskFactory {
    abstract public Task create(JsonObject json);
}
