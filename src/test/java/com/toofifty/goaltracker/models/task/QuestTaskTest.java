package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import net.runelite.api.Quest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestTaskTest {

    @Test
    void toString_shouldReturnTheQuestName() {
        QuestTask task = QuestTask.builder().quest(Quest.ANIMAL_MAGNETISM).build();

        assertEquals(Quest.ANIMAL_MAGNETISM.getName(), task.toString());
    }

    @Test
    void getType_shouldReturnQuest() {
        QuestTask task = QuestTask.builder().build();

        assertEquals(TaskType.QUEST, task.getType());
    }
}