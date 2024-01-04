package com.toofifty.goaltracker.models.enums;

import net.runelite.api.QuestState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void fromQuestState_shouldReturnCompletedForFinishedQuests() {
        assertEquals(Status.COMPLETED, Status.fromQuestState(QuestState.FINISHED));
    }

    @Test
    void fromQuestState_shouldReturnInProgressForInProgressQuests() {
        assertEquals(Status.IN_PROGRESS, Status.fromQuestState(QuestState.IN_PROGRESS));
    }

    @Test
    void fromQuestState_shouldReturnNotStartedForNotStartedQuests() {
        assertEquals(Status.NOT_STARTED, Status.fromQuestState(QuestState.NOT_STARTED));
    }

    @Test
    void isCompleted_shouldReturnTrueForCompleted() {
        assertTrue(Status.COMPLETED.isCompleted());
    }

    @Test
    void isCompleted_shouldReturnFalseForAnythingElse() {
        assertFalse(Status.NOT_STARTED.isCompleted());
    }

    @Test
    void isInProgress_shouldReturnTrueForInProgress() {
        assertTrue(Status.IN_PROGRESS.isInProgress());
    }

    @Test
    void isInProgress_shouldReturnFalseForAnythingElse() {
        assertFalse(Status.COMPLETED.isInProgress());
    }

    @Test
    void isNotStarted_shouldReturnTrueForNotStarted() {
        assertTrue(Status.NOT_STARTED.isNotStarted());
    }

    @Test
    void isNotStarted_shouldReturnFalseForAnythingElse() {
        assertFalse(Status.COMPLETED.isNotStarted());
    }

    @Test
    void toString_shouldReturnTheName() {
        assertEquals(Status.COMPLETED.getName(), Status.COMPLETED.toString());
    }
}