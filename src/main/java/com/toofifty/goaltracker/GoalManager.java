package com.toofifty.goaltracker;

import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.enums.TaskType;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.utils.ReorderableList;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Singleton
public class GoalManager
{
    @Inject
    private GoalTrackerConfig config;

    @Inject
    private GoalSerializer goalSerializer;

    @Getter
    private final ReorderableList<Goal> goals = new ReorderableList<>();

    public Goal createGoal()
    {
        Goal goal = Goal.builder().build();
        goals.add(goal);
        return goal;
    }

    @SuppressWarnings("unchecked")
    public <T extends Task> List<T> getTasksByTypeAndAnyStatus(TaskType type, Status ...statuses)
    {
        List<T> tasks = new ArrayList<>();
        for (Goal goal : goals) {
            tasks.addAll((List<T>) goal.getTasks().stream()
                .filter((task) -> task.getType() == type && Arrays.stream(statuses).anyMatch((status) -> status == task.getStatus()))
                .collect(Collectors.toList())
            );
        }
        return tasks;
    }

    public <T extends Task> List<T> getIncompleteTasksByType(TaskType type) {
        return this.getTasksByTypeAndAnyStatus(type, Status.NOT_STARTED, Status.IN_PROGRESS);
    }

    public void save()
    {
        config.goalTrackerData(goalSerializer.serialize(goals));

        log.info("Saved " + goals.size() + " goals");
    }

    public void load()
    {
        try {
            this.goals.clear();
            this.goals.addAll(goalSerializer.deserialize(config.goalTrackerData()));

            log.info("Loaded " + this.goals.size() + " goals");
        } catch (Exception e) {
            log.error("Failed to load goals!");
        }
    }
}
