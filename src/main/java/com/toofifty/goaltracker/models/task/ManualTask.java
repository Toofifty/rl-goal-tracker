package com.toofifty.goaltracker.models.task;

import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.enums.TaskType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class ManualTask extends Task
{
    private String description;

    public void toggle()
    {
        this.setStatus(this.getStatus().isNotStarted() ? Status.COMPLETED : Status.NOT_STARTED);
    }

    @Override
    public String toString()
    {
        return description;
    }

    @Override
    public TaskType getType()
    {
        return TaskType.MANUAL;
    }
}
