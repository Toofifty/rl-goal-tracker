package com.toofifty.goaltracker.goal;

import com.toofifty.goaltracker.goal.factory.ItemTaskFactory;
import com.toofifty.goaltracker.goal.factory.ManualTaskFactory;
import com.toofifty.goaltracker.goal.factory.QuestTaskFactory;
import com.toofifty.goaltracker.goal.factory.SkillLevelTaskFactory;
import com.toofifty.goaltracker.goal.factory.SkillXpTaskFactory;
import com.toofifty.goaltracker.goal.factory.TaskFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TaskType
{
    MANUAL("manual", ManualTaskFactory.class),
    SKILL_LEVEL("skill_level", SkillLevelTaskFactory.class),
    SKILL_XP("skill_xp", SkillXpTaskFactory.class),
    QUEST("quest", QuestTaskFactory.class),
    ITEM("item", ItemTaskFactory.class);

    @Getter
    private final String name;

    @Getter
    private final Class<? extends TaskFactory<? extends Task>> factory;

    public static TaskType fromString(String name)
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
