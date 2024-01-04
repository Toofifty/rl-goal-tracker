package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.TaskType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTaskTest {

    @Test
    void toString_shouldSupportItems() {
        ItemTask task = ItemTask.builder().quantity(1).itemName("Feather").build();

        assertEquals("Feather", task.toString());
    }

    @Test
    void toString_shouldSupportMultipleItems() {
        ItemTask task = ItemTask.builder().quantity(100).itemName("Feather").build();

        assertEquals("100 x Feather", task.toString());
    }

    @Test
    void toString_shouldSupportHavingSomeItemsAcquired() {
        ItemTask task = ItemTask.builder().acquired(50).quantity(100).itemName("Feather").build();

        assertEquals("50/100 x Feather", task.toString());
    }

    @Test
    void toString_shouldSupportTheAcquiredItemsExceedingTheQuantity() {
        ItemTask task = ItemTask.builder().acquired(150).quantity(100).itemName("Feather").build();

        assertEquals("100 x Feather", task.toString());
    }

    @Test
    void getType_shouldReturnItem() {
        ItemTask task = ItemTask.builder().build();

        assertEquals(TaskType.ITEM, task.getType());
    }
}