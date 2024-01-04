package com.toofifty.goaltracker.models.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTypeTest {
    @Test
    void fromString_shouldConvertTheNameToTheEnum() {
        assertEquals(TaskType.MANUAL, TaskType.fromString(TaskType.MANUAL.getName()));
    }

    @Test
    void fromString_shouldThrowAnExceptionWhenUnknown() {
        assertThrows(IllegalStateException.class, () -> TaskType.fromString("oops!"));
    }

    @Test
    void toString_shouldReturnName() {
        assertEquals(TaskType.MANUAL.getName(), TaskType.MANUAL.toString());
    }
}