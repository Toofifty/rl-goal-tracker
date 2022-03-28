package com.toofifty.goaltracker.goal;

import com.toofifty.goaltracker.goal.factory.*;
import lombok.Getter;

public enum TaskType
{
    MANUAL("manual", new ManualTaskFactory()),
    SKILL_LEVEL("skill_level", new SkillLevelTaskFactory()),
    SKILL_XP("skill_xp", new SkillXpTaskFactory()),
    QUEST("quest", new QuestTaskFactory()),
    ITEM("item", new ItemTaskFactory());

    @Getter
    private final String name;
    @Getter
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
}
