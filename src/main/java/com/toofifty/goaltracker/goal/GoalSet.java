package com.toofifty.goaltracker.goal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GoalSet {
    @Setter
    @Getter
    private String description = "New goal";

    @Setter
    @Getter
    private int displayOrder = -1;

    @Getter
    private List<AbstractGoal> goals = new ArrayList<>();

    public GoalSet add(AbstractGoal goal) {
        goals.add(goal);
        return this;
    }

    public GoalSet remove(AbstractGoal goal) {
        goals.remove(goal);
        return this;
    }

    public Boolean isFirst(AbstractGoal goal) {
        return goals.get(0) == goal;
    }

    public Boolean isLast(AbstractGoal goal) {
        return goals.get(goals.size() - 1) == goal;
    }

    public GoalSet move(AbstractGoal goal, int offset) {
        int index = goals.indexOf(goal);
        goals.remove(goal);
        goals.add(index + offset, goal);
        return this;
    }

    public GoalSet moveToTop(AbstractGoal goal) {
        goals.remove(goal);
        goals.add(0, goal);
        return this;
    }

    public GoalSet moveToBottom(AbstractGoal goal) {
        goals.remove(goal);
        goals.add(goal);
        return this;
    }

    public List<AbstractGoal> getComplete() {
        return filterBy(AbstractGoal::isComplete);
    }

    public List<AbstractGoal> getIncomplete() {
        return filterBy(goal -> !goal.isComplete());
    }

    public Boolean isComplete() {
        return this.getComplete().size() == this.getGoals().size();
    }

    public Boolean isInProgress() {
        return this.getComplete().size() > 0;
    }

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("description", description);
        json.addProperty("display_order", displayOrder);
        JsonArray items = new JsonArray();
        goals.forEach(goal -> items.add(goal.serialize()));
        json.add("items", items);
        return json;
    }

    private List<AbstractGoal> filterBy(Predicate<AbstractGoal> predicate) {
        return goals.stream()
                .filter(predicate)
                .collect(Collectors.<AbstractGoal>toList());
    }
}
