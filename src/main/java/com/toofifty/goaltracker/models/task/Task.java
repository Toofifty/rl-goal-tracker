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

    @Builder.Default
    @SerializedName("indent_level")
    private int indentLevel = 0;

    public boolean isDone() {
        return Status.COMPLETED.equals(this.status);
    }

    public void indent() {
        if (isFullyIndented()) return;

        indentLevel += 1;
    }

    public void unindent() {
        if (isNotIndented()) return;

        indentLevel -= 1;
    }

    public boolean isIndented() {
        return indentLevel > 0;
    }

    public boolean isNotIndented() {
        return !isIndented();
    }

    public boolean isFullyIndented() {
        return indentLevel == 3;
    }

    public boolean isNotFullyIndented() {
        return !isFullyIndented();
    }

    @Override
    abstract public String toString();

    abstract public TaskType getType();
}
