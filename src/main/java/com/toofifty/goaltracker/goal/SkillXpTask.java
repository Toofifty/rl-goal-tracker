package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;

import java.awt.image.BufferedImage;

public class SkillXpTask extends Task
{
    @Getter
    @Setter
    private Skill skill;
    @Getter
    @Setter
    private int xp;

    public SkillXpTask(Goal goal)
    {
        super(goal);
    }

    @Override
    public Boolean check(Client client)
    {
        return client.getSkillExperience(skill) >= xp;
    }

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
    public BufferedImage getIcon()
    {
        return new SkillIconManager().getSkillImage(skill);
    }
}
