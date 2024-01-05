package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillXpTaskTest {
    @Test
    void toString_shouldReturnTheXPAndSkillName() {
        SkillXpTask task = SkillXpTask.builder().skill(Skill.ATTACK).xp(1234).build();

        assertEquals("1234 Attack XP", task.toString());
    }

    @Test
    void getType_shouldReturnSkill() {
        SkillXpTask task = SkillXpTask.builder().build();

        assertEquals(TaskType.SKILL_XP, task.getType());
    }
}