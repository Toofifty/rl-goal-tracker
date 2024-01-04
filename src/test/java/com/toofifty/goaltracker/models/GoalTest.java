package com.toofifty.goaltracker.models;

import com.toofifty.goaltracker.utils.ReorderableList;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.task.ItemTask;
import com.toofifty.goaltracker.models.task.ManualTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoalTest {
    @Test
    void isAnyStatus_shouldReturnTrueIfAllStatusesMatch() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(ManualTask.builder().build())).build();

        assertTrue(goal.isAnyStatus(Status.NOT_STARTED));
    }

    @Test
    void isAnyStatus_shouldReturnTrueIfAnyTaskMatches() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(
                ManualTask.builder().build(),
                ManualTask.builder().status(Status.COMPLETED).build()
        )).build();

        assertTrue(goal.isAnyStatus(Status.NOT_STARTED));
    }

    @Test
    void isAnyStatus_shouldReturnTrueIfAnyStatusesMatch() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(
                ManualTask.builder().build(),
                ManualTask.builder().status(Status.COMPLETED).build()
        )).build();

        assertTrue(goal.isAnyStatus(Status.IN_PROGRESS, Status.COMPLETED));
    }

    @Test
    void isAnyStatus_shouldReturnFalseIfNoneOfTheStatusesMatch() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(
                ManualTask.builder().build()
        )).build();

        assertFalse(goal.isAnyStatus(Status.IN_PROGRESS, Status.COMPLETED));
    }

    @Test
    void isStatus_shouldReturnTrueIfAllTasksMatchAGivenStatus() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(ManualTask.builder().build())).build();

        assertTrue(goal.isStatus(Status.NOT_STARTED));
    }

    @Test
    void isStatus_shouldReturnFalseIfNoTasksMatchAGivenStatus() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(ManualTask.builder().status(Status.COMPLETED).build())).build();

        assertFalse(goal.isStatus(Status.NOT_STARTED));
    }

    @Test
    void isStatus_shouldReturnFalseIfSomeTasksDoNotMatchAGivenStatus() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(
                ManualTask.builder().build(),
                ManualTask.builder().status(Status.COMPLETED).build()
        )).build();

        assertFalse(goal.isStatus(Status.NOT_STARTED));
    }

    @Test
    void getStatus_shouldSupportNotBeingStarted() {
        Goal goal = Goal.builder().tasks(ReorderableList.from(ManualTask.builder().build())).build();

        assertEquals(Status.NOT_STARTED, goal.getStatus());
    }

    @Test
    void getStatus_shouldSupportBeingCompleted() {
        Goal goal = Goal.builder()
                .tasks(ReorderableList.from(ManualTask.builder().status(Status.COMPLETED).build()))
                .build();

        assertEquals(Status.COMPLETED, goal.getStatus());
    }

    @Test
    void getStatus_shouldSupportBeingInProgress() {
        Goal goal = Goal.builder()
                .tasks(ReorderableList.from(
                        ManualTask.builder().status(Status.COMPLETED).build(),
                        ManualTask.builder().status(Status.NOT_STARTED).build()
                ))
                .build();

        assertEquals(Status.IN_PROGRESS, goal.getStatus());
    }

    @Test
    void getStatus_shouldSupportPartialCompletionOfATask() {
        Goal goal = Goal.builder()
                .tasks(ReorderableList.from(
                        ItemTask.builder().status(Status.IN_PROGRESS).build()
                ))
                .build();

        assertEquals(Status.IN_PROGRESS, goal.getStatus());
    }

    @Test
    void getComplete_shouldReturnTheCompletedTasks() {
        Goal goal = Goal.builder()
                .tasks(ReorderableList.from(
                        ManualTask.builder().status(Status.COMPLETED).build(),
                        ManualTask.builder().status(Status.NOT_STARTED).build()
                ))
                .build();

        assertEquals(1, goal.getComplete().size());
    }
}