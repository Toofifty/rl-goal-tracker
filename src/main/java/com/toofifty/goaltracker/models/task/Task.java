package com.toofifty.goaltracker.models.task;

import com.google.gson.annotations.SerializedName;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.enums.TaskType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class Task
{
    @Builder.Default
    @SerializedName("previous_result")
    private Status status = Status.NOT_STARTED;

    @Builder.Default
    @SerializedName("has_been_notified")
    private boolean notified = false;

    public boolean isDone() {
        return Status.COMPLETED.equals(this.status);
    }

    @Override
    abstract public String toString();

    abstract public TaskType getType();
}
