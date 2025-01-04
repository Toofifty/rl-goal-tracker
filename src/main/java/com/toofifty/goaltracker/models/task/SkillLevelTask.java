package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.runelite.api.Skill;

@Setter
@Getter
@SuperBuilder
public class SkillLevelTask extends Task
{
    private Skill skill;
    private int level;
    @Builder.Default
    private int current = 1;

    @Override
    public String toString()
    {
        if (current > 1 && current < level) {
            return String.format("%d/%d %s", current, level, skill.getName());
        }

        return String.format("%d %s", level, skill.getName());
    }

    @Override
    public TaskType getType()
    {
        return TaskType.SKILL_LEVEL;
    }
}
