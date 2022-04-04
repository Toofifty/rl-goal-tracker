package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.SkillXpTask;
import net.runelite.api.Skill;

public class SkillXpTaskFactory extends TaskFactory<SkillXpTask>
{
    @Override
    public SkillXpTask create(JsonObject json)
    {
        return create(Skill.valueOf(json.get("skill")
            .getAsString()
            .toUpperCase()), json.get("xp").getAsInt());
    }

    @Override
    public SkillXpTask create()
    {
        return new SkillXpTask();
    }

    public SkillXpTask create(Skill skill, int xp)
    {
        SkillXpTask task = create();
        task.setSkill(skill);
        task.setXp(xp);
        return task;
    }
}
