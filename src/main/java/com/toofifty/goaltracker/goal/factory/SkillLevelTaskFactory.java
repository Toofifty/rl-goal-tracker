package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import net.runelite.api.Skill;

public class SkillLevelTaskFactory extends TaskFactory<SkillLevelTask>
{
    @Override
    public SkillLevelTask create(JsonObject json)
    {
        return create(
            Skill.valueOf(json.get("skill").getAsString().toUpperCase()),
            json.get("level").getAsInt()
        );
    }

    @Override
    public SkillLevelTask create()
    {
        return new SkillLevelTask();
    }

    public SkillLevelTask create(Skill skill, int level)
    {
        SkillLevelTask task = create();
        task.setSkill(skill);
        task.setLevel(level);
        return task;
    }
}
