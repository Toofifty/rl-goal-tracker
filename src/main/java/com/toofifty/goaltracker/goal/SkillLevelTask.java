package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;

import java.awt.image.BufferedImage;

public class SkillLevelTask extends Task
{
    @Setter
    @Getter
    private Skill skill;
    @Getter
    @Setter
    private int level;

    public SkillLevelTask(Goal goal)
    {
        super(goal);
    }

    @Override
    public String toString()
    {
        return level + " " + skill.getName();
    }

    @Override
    public Boolean check(Client client)
    {
        return client.getRealSkillLevel(skill) >= level;
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
    public BufferedImage getIcon()
    {
        return new SkillIconManager().getSkillImage(skill);
    }
}
