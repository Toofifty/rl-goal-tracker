package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;

public class SkillXpTask extends Task
{
    private final Client client;
    private final SkillIconManager skillIconManager;

    @Getter
    @Setter
    private Skill skill;

    @Getter
    @Setter
    private int xp;

    public SkillXpTask(
        Client client, SkillIconManager skillIconManager, Goal goal)
    {
        super(goal);
        this.client = client;
        this.skillIconManager = skillIconManager;
    }

    @Override
    public TaskStatus check()
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return result;
        }

        return result = (client.getSkillExperience(skill) >= xp ? TaskStatus.COMPLETED : TaskStatus.NOT_STARTED);
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
        return skillIconManager.getSkillImage(skill);
    }
}
