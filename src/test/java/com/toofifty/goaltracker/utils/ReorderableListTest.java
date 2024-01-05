package com.toofifty.goaltracker.utils;

import com.toofifty.goaltracker.utils.ReorderableList;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReorderableListTest {
    @Test
    void moveDown_shouldMoveDown() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        list.moveDown("Welcome");

        assertEquals(3, list.size());
        assertEquals("to", list.get(0));
        assertEquals("Welcome", list.get(1));
        assertEquals("RuneScape", list.get(2));
    }

    @Test
    void moveUp_shouldMoveUp() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        list.moveUp("RuneScape");

        assertEquals(3, list.size());
        assertEquals("Welcome", list.get(0));
        assertEquals("RuneScape", list.get(1));
        assertEquals("to", list.get(2));
    }

    @Test
    void moveToTop_shouldMoveToTheTop() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        list.moveToTop("RuneScape");

        assertEquals(3, list.size());
        assertEquals("RuneScape", list.get(0));
        assertEquals("Welcome", list.get(1));
        assertEquals("to", list.get(2));
    }

    @Test
    void moveToBottom_shouldMoveToTheBottom() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        list.moveToBottom("Welcome");

        assertEquals(3, list.size());
        assertEquals("to", list.get(0));
        assertEquals("RuneScape", list.get(1));
        assertEquals("Welcome", list.get(2));
    }

    @Test
    void isFirst_shouldReturnTrueIfFirst() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        assertTrue(list.isFirst("Welcome"));
    }

    @Test
    void isFirst_shouldReturnFalseIfNotFirst() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        assertFalse(list.isFirst("RuneScape"));
    }

    @Test
    void isLast_shouldReturnTrueIfLast() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        assertTrue(list.isLast("RuneScape"));
    }

    @Test
    void isLast_shouldReturnFalseIfNotLast() {
        ReorderableList<String> list = new ReorderableList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        assertFalse(list.isLast("Welcome"));
    }

    @Test
    void from_shouldConvertAListToAReorderableList() {
        List<String> list = new ArrayList<>();

        list.add("Welcome");
        list.add("to");
        list.add("RuneScape");

        ReorderableList<String> reorderableList = ReorderableList.from(list);

        assertEquals(3, reorderableList.size());
        assertEquals("Welcome", reorderableList.get(0));
        assertEquals("to", reorderableList.get(1));
        assertEquals("RuneScape", reorderableList.get(2));
    }
}