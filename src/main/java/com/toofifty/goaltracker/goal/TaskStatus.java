package com.toofifty.goaltracker.goal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskStatus
{
    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    @Getter
    private final String name;

    public boolean isCompleted()
    {
        return this == COMPLETED;
    }

    public boolean isInProgress()
    {
        return this == IN_PROGRESS;
    }

    public boolean isNotStarted()
    {
        return this == NOT_STARTED;
    }
}
