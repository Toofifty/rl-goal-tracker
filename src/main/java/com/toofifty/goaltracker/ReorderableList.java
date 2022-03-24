package com.toofifty.goaltracker;

import java.util.List;

public interface ReorderableList<T>
{
    List<T> getAll();

    void add(T item);

    void remove(T item);

    void move(T item, int offset);

    void moveToTop(T item);

    void moveToBottom(T item);

    Boolean isFirst(T item);

    Boolean isLast(T item);
}
