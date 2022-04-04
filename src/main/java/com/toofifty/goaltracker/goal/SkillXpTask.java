package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.SkillXpTaskFactory;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Skill;

public class SkillXpTask extends Task
{
    @Getter
    @Setter
    private Skill skill;

    @Getter
    @Setter
    private int xp;

    @Override
    public String toString()
    {
        return String.format("%,d", xp) + " " + skill.getName() + " XP";
    }

    @Override
    public TaskType getType()
    {
        return TaskType.SKILL_XP;
    }

    @Override
    protected JsonObject addSerializedProperties(JsonObject json)
    {
        json.addProperty("skill", skill.getName());
        json.addProperty("xp", xp);
        return json;
    }

    @Override
    public Class<SkillXpTaskFactory> getFactoryClass()
    {
        return SkillXpTaskFactory.class;
    }
}
