package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillLevelTaskTest {

    @Test
    void toString_shouldReturnTheLevelAndSkillName() {
        SkillLevelTask task = SkillLevelTask.builder().skill(Skill.ATTACK).level(99).build();

        assertEquals("99 Attack", task.toString());
    }

    @Test
    void getType_shouldReturnSkill() {
        SkillLevelTask task = SkillLevelTask.builder().build();

        assertEquals(TaskType.SKILL_LEVEL, task.getType());
    }
}