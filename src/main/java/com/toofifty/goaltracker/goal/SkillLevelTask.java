package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;

public class SkillLevelTask extends Task
{
    private final Client client;
    private final SkillIconManager skillIconManager;

    @Setter
    @Getter
    private Skill skill;
    @Getter
    @Setter
    private int level;

    public SkillLevelTask(
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

        return result = (client.getRealSkillLevel(skill) >= level ? TaskStatus.COMPLETED : TaskStatus.NOT_STARTED);
    }

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
    public BufferedImage getIcon()
    {
        return skillIconManager.getSkillImage(skill);
    }
}
