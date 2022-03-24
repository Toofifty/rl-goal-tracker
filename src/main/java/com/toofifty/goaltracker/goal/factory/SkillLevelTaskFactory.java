package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.Task;
import net.runelite.api.Skill;

public class SkillLevelTaskFactory extends TaskFactory
{
    @Override
    protected Task createObject(Goal goal, JsonObject json)
    {
        SkillLevelTask task = new SkillLevelTask(goal);
        task.setLevel(json.get("level").getAsInt());
        task.setSkill(Skill.valueOf(json.get("skill")
            .getAsString()
            .toUpperCase()));
        return task;
    }
}
