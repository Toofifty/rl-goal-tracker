package com.toofifty.goaltracker.models.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.runelite.api.QuestState;

@Getter
@AllArgsConstructor
public enum Status
{
    @SerializedName("not_started")
    NOT_STARTED("not_started"),
    @SerializedName("in_progress")
    IN_PROGRESS("in_progress"),
    @SerializedName("completed")
    COMPLETED("completed");

    private final String name;

    public static Status fromQuestState(QuestState questState)
    {
        switch (questState) {
            case IN_PROGRESS:
                return Status.IN_PROGRESS;
            case FINISHED:
                return Status.COMPLETED;
            default:
                return Status.NOT_STARTED;
        }
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

    @Override
    public String toString() {
        return this.name;
    }
}
