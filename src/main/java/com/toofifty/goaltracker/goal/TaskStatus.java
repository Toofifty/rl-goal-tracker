package com.toofifty.goaltracker.goal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.QuestState;

@AllArgsConstructor
public enum TaskStatus
{
    NOT_STARTED("not_started"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    @Getter
    private final String name;

    public static TaskStatus fromQuestState(QuestState questState)
    {
        switch (questState) {
            case IN_PROGRESS:
                return TaskStatus.IN_PROGRESS;
            case FINISHED:
                return TaskStatus.COMPLETED;
        }
        return TaskStatus.NOT_STARTED;
    }

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
