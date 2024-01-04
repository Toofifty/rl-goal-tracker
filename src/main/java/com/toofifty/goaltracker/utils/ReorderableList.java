package com.toofifty.goaltracker.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ReorderableList<T> extends ArrayList<T>
{
    private void move(T item, int offset) {
        int i = this.indexOf(item);

        Collections.swap(this, i, i + offset);
    }

    public void moveUp(T item) {
        this.move(item, -1);
    }

    public void moveDown(T item) {
        this.move(item, 1);
    }

    public void moveToTop(T item)
    {
        this.remove(item);
        this.add(0, item);
    }

    public void moveToBottom(T item)
    {
        this.remove(item);
        this.add(item);
    }

    public Boolean isFirst(T item)
    {
        return !this.isEmpty() && this.get(0) == item;
    }

    public Boolean isLast(T item)
    {
        return !this.isEmpty() && this.get(this.size() - 1) == item;
    }

    public static <T> ReorderableList<T> from(List<T> items) {
        ReorderableList<T> list = new ReorderableList<>();
        list.addAll(items);
        return list;
    }

    @SafeVarargs
    public static <T> ReorderableList<T> from(T ...items) {
        return ReorderableList.from(Arrays.asList(items));
    }
}
