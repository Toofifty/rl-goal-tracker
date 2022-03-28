package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillXpTask;
import net.runelite.api.Skill;

public class SkillXpTaskFactory extends TaskFactory
{
    public SkillXpTaskFactory(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected SkillXpTask createObjectFromJson(JsonObject json)
    {
        return create(Skill.valueOf(json.get("skill")
            .getAsString()
            .toUpperCase()), json.get("xp").getAsInt());
    }

    public SkillXpTask create(Skill skill, int xp)
    {
        SkillXpTask task = new SkillXpTask(
            plugin.getClient(), plugin.getSkillIconManager(), goal);
        task.setSkill(skill);
        task.setXp(xp);
        return task;
    }
}
