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
    void indent_shouldIndentIfNotFullyIndented() {
        ManualTask task = ManualTask.builder().status(Status.COMPLETED).build();

        task.indent();

        assertEquals(task.getIndentLevel(), 1);
    }

    @Test
    void indent_shouldNotIndentIfFullyIndented() {
        ManualTask task = ManualTask.builder().indentLevel(3).status(Status.COMPLETED).build();

        task.indent();

        assertEquals(task.getIndentLevel(), 3);
    }

    @Test
    void unindent_shouldUnindentIfIndented() {
        ManualTask task = ManualTask.builder().indentLevel(2).status(Status.COMPLETED).build();

        task.unindent();

        assertEquals(task.getIndentLevel(), 1);
    }

    @Test
    void unindent_shouldNotUnindentIfNotIndented() {
        ManualTask task = ManualTask.builder().status(Status.COMPLETED).build();

        task.unindent();

        assertEquals(task.getIndentLevel(), 0);
    }
}