package com.toofifty.goaltracker.goal.factory;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import net.runelite.api.Skill;

public class SkillLevelTaskFactory extends TaskFactory
{
    public SkillLevelTaskFactory(
        GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal);
    }

    @Override
    protected SkillLevelTask createObjectFromJson(JsonObject json)
    {
        return create(Skill.valueOf(json.get("skill").getAsString()
                .toUpperCase()),
            json.get("level").getAsInt()
        );
    }

    public SkillLevelTask create(Skill skill, int level)
    {
        SkillLevelTask task = new SkillLevelTask(plugin.getClient(),
            plugin.getSkillIconManager(), goal
        );
        task.setSkill(skill);
        task.setLevel(level);
        return task;
    }
}
