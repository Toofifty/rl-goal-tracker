package com.toofifty.goaltracker.models.enums;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskType
{
    @SerializedName("manual")
    MANUAL("manual"),
    @SerializedName("skill_level")
    SKILL_LEVEL("skill_level"),
    @SerializedName("skill_xp")
    SKILL_XP("skill_xp"),
    @SerializedName("quest")
    QUEST("quest"),
    @SerializedName("item")
    ITEM("item");

    private final String name;

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
