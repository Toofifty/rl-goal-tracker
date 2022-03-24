package com.toofifty.goaltracker.goal;

import com.google.gson.JsonObject;
import com.toofifty.goaltracker.goal.factory.*;
import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.api.GameState;

import java.awt.image.BufferedImage;

public abstract class Task
{
    @Getter
    private Goal goal;

    @Setter
    private Boolean previousResult = false;

    private Boolean hasBeenNotified = false;

    public Task(Goal goal)
    {
        this.goal = goal;
    }

    public void hasBeenNotified(Boolean hasBeenNotified)
    {
        this.hasBeenNotified = hasBeenNotified;
    }

    public Boolean hasBeenNotified()
    {
        return hasBeenNotified;
    }

    public Boolean checkSafe(Client client)
    {
        if (client.getGameState() == GameState.LOGGED_IN && (!requiresClientThread() || client
            .isClientThread())) {
            setPreviousResult(check(client));
        }
        return previousResult;
    }

    protected Boolean requiresClientThread()
    {
        return false;
    }

    abstract public Boolean check(Client client);

    @Override
    abstract public String toString();

    public JsonObject serialize()
    {
        JsonObject json = new JsonObject();
        json.addProperty("type", getType().name);
        json.addProperty("previous_result", previousResult);
        json.addProperty("has_been_notified", hasBeenNotified);
        return addSerializedProperties(json);
    }

    abstract public TaskType getType();

    abstract protected JsonObject addSerializedProperties(JsonObject json);

    public Boolean hasIcon()
    {
        return this.getIcon() != null;
    }

    abstract public BufferedImage getIcon();

    public void save()
    {
        goal.save();
    }

    public enum TaskType
    {
        MANUAL("manual", new ManualTaskFactory()),
        SKILL_LEVEL("skill_level", new SkillLevelTaskFactory()),
        SKILL_XP("skill_xp", new SkillXpTaskFactory()),
        QUEST("quest", new QuestTaskFactory());

        private final String name;
        private final TaskFactory factory;

        TaskType(String name, TaskFactory factory)
        {
            this.name = name;
            this.factory = factory;
        }

        static TaskType fromString(String name)
        {
            for (TaskType type : TaskType.values()) {
                if (type.toString().equals(name)) {
                    return type;
                }
            }
            throw new IllegalStateException("Invalid task type " + name);
        }

        @Override
        public String toString()
        {
            return this.name;
        }

        public TaskFactory getFactory()
        {
            return this.factory;
        }
    }
}
