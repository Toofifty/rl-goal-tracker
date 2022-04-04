package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.SkillLevelTaskFactory;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Skill;

public class SkillLevelTask extends Task
{
    @Setter
    @Getter
    private Skill skill;

    @Getter
    @Setter
    private int level;

    @Override
    public String toString()
    {
        return level + " " + skill.getName();
    }

    @Override
    public TaskType getType()
    {
        return TaskType.SKILL_LEVEL;
    }

    @Override
    protected JsonObject addSerializedProperties(JsonObject json)
    {
        json.addProperty("skill", skill.getName());
        json.addProperty("level", level);
        return json;
    }

    @Override
    public Class<SkillLevelTaskFactory> getFactoryClass()
    {
        return SkillLevelTaskFactory.class;
    }
}
