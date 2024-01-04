package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void isDone_shouldBeFalseIfStatusIsNotStarted() {
        ManualTask task = ManualTask.builder().status(Status.NOT_STARTED).build();

        assertFalse(task.isDone());
    }

    @Test
    void isDone_shouldBeFalseIfStatusIsInProgress() {
        ManualTask task = ManualTask.builder().status(Status.IN_PROGRESS).build();

        assertFalse(task.isDone());
    }

    @Test
    void isDone_shouldBeTrueIfStatusIsCompleted() {
        ManualTask task = ManualTask.builder().status(Status.COMPLETED).build();

        assertTrue(task.isDone());
    }
}