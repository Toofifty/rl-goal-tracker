package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.runelite.api.Skill;

@Getter
@Setter
@SuperBuilder
public class SkillXpTask extends Task
{
    private Skill skill;
    private int xp;
    @Builder.Default
    private int current = 1;

    @Override
    public String toString()
    {
        if (current > 1 && current < xp) {
            return String.format("%d/%d %s XP", current, xp, skill.getName());
        }

        return String.format("%d %s XP", xp, skill.getName());
    }

    @Override
    public TaskType getType()
    {
        return TaskType.SKILL_XP;
    }
}
