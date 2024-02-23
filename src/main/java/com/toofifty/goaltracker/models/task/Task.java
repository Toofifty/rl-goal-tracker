package com.toofifty.goaltracker.models.task;

import com.google.common.primitives.UnsignedInteger;
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
    @Getter
    private int indentLevel = 0;

    transient final private int MAX_INDENT = 3;

    public boolean isDone() {
        return Status.COMPLETED.equals(this.status);
    }


    public void indent()
    {
        if (indentLevel < MAX_INDENT)
        {
            indentLevel += 1;
        }
    }

    public void unindent()
    {
        if (indentLevel > 0)
        {
            indentLevel -= 1;
        }
    }

    @Override
    abstract public String toString();

    abstract public TaskType getType();
}
