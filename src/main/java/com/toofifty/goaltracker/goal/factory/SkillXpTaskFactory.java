package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillXpTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.api.Skill;

public class SkillXpTaskFactory extends TaskFactory
{
    @Override
    protected Task createObject(Goal goal, JsonObject json)
    {
        SkillXpTask task = new SkillXpTask(goal);
        task.setXp(json.get("xp").getAsInt());
        task.setSkill(Skill.valueOf(json.get("skill")
            .getAsString()
            .toUpperCase()));
        return task;
    }
}
