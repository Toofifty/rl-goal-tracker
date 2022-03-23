package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.ManualTaskFactory;
import com.toofifty.goaltracker.goal.factory.TaskFactory;

import java.awt.image.BufferedImage;

public abstract class Task {
    private Goal goal;

    public Task(Goal goal) {
        this.goal = goal;
    }

    @Override
    abstract public String toString();

    abstract public TaskType getType();

    abstract public Boolean isComplete();

    abstract public BufferedImage getIcon();

    abstract protected JsonObject addSerializedProperties(JsonObject json);

    public JsonObject serialize() {
        JsonObject json = new JsonObject();
        json.addProperty("type", getType().name);
        return addSerializedProperties(json);
    }

    public Boolean hasIcon() {
        return this.getIcon() != null;
    }

    public void save() {
        goal.save();
    }

    public enum TaskType {
        MANUAL("manual", new ManualTaskFactory());

        private final String name;
        private final TaskFactory factory;

        TaskType(String name, TaskFactory factory) {
            this.name = name;
            this.factory = factory;
        }

        static TaskType fromString(String name) {
            for (TaskType type : TaskType.values()) {
                if (type.toString().equals(name)) {
                    return type;
                }
            }
            throw new IllegalStateException("Invalid task type " + name);
        }

        public TaskFactory getFactory() {
            return this.factory;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
