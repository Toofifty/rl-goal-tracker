package com.toofifty.goaltracker.models;

import com.google.gson.annotations.SerializedName;
import com.toofifty.goaltracker.utils.ReorderableList;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.task.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Setter
@Getter
@SuperBuilder
public class Goal
{
    @Builder.Default
    private String description = "New goal";

    @Builder.Default
    private int displayOrder = -1;

    @SerializedName("items")
    @Builder.Default
    private ReorderableList<Task> tasks = new ReorderableList<>();

    private List<Task> filterBy(Predicate<Task> predicate)
    {
        return this.getTasks().stream().filter(predicate).collect(Collectors.toList());
    }

    public boolean isStatus(Status status) {
        return this.getTasks().stream().allMatch((task) -> task.getStatus() == status);
    }

    public boolean isAnyStatus(Status ...statuses) {
        return this.getTasks().stream().anyMatch((task) -> Arrays.stream(statuses).anyMatch((status) -> status == task.getStatus()));
    }

    public List<Task> getComplete() {
        return this.filterBy(Task::isDone);
    }

    public Status getStatus() {
        if (this.isStatus(Status.COMPLETED)) {
            return Status.COMPLETED;
        }

        if (this.isAnyStatus(Status.IN_PROGRESS, Status.COMPLETED)) {
            return Status.IN_PROGRESS;
        }

        return Status.NOT_STARTED;
    }
}
