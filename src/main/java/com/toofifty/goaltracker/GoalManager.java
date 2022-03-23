package com.toofifty.goaltracker;

import com.toofifty.goaltracker.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class GoalManager implements ReorderableList<Goal> {
    private List<Goal> goals;

    private GoalTrackerConfig config;

    private GoalSerializer goalSerializer = new GoalSerializer();

    public GoalManager(GoalTrackerConfig config) {
        this.config = config;
    }

    public void save() {
        config.goalTrackerData(goalSerializer.serialize(goals));

        System.out.println("Saved " + goals.size() + " goals");
    }

    public void load() {
        try {
            goals = goalSerializer.deserialize(this, config.goalTrackerData());
        } catch (IllegalStateException e) {
            goals = new ArrayList<>();
            throw e;
        }
        System.out.println("Loaded " + goals.size() + " goals");
    }

    public Goal createGoal() {
        Goal goal = new Goal(this);
        add(goal);
        return goal;
    }

    @Override
    public List<Goal> getAll() {
        return this.goals;
    }

    @Override
    public void add(Goal goal) {
        goals.add(goal);
    }

    @Override
    public void remove(Goal goal) {
        goals.remove(goal);
    }

    @Override
    public void move(Goal goal, int offset) {
        int index = goals.indexOf(goal);
        goals.remove(goal);
        goals.add(index + offset, goal);
    }

    @Override
    public void moveToTop(Goal goal) {
        goals.remove(goal);
        goals.add(0, goal);
    }

    @Override
    public void moveToBottom(Goal goal) {
        goals.remove(goal);
        goals.add(goal);
    }

    @Override
    public Boolean isFirst(Goal goal) {
        return goals.get(0) == goal;
    }

    @Override
    public Boolean isLast(Goal goal) {
        return goals.get(goals.size() - 1) == goal;
    }
}
