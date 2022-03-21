package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.AbstractGoal;

abstract public class AbstractGoalFactory {
    abstract public AbstractGoal create(JsonObject json);
}
