package com.toofifty.goaltracker.adapters;

import com.google.gson.*;
import com.toofifty.goaltracker.models.enums.TaskType;
import com.toofifty.goaltracker.models.task.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;

@Slf4j
public class TaskAdapter implements JsonSerializer<Task>, JsonDeserializer<Task> {

    @Override
    public JsonElement serialize(Task src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = context.serialize(src).getAsJsonObject();
        json.addProperty("type", context.serialize(src.getType()).getAsString());
        return json;
    }

    @Override
    public Task deserialize(JsonElement json, Type typeOfT,
                               JsonDeserializationContext context) throws JsonParseException  {
        TaskType taskType = TaskType.fromString(json.getAsJsonObject().get("type").getAsString());
        switch (taskType) {
            case SKILL_XP:
                return context.deserialize(json, SkillXpTask.class);
            case ITEM:
                return context.deserialize(json, ItemTask.class);
            case MANUAL:
                return context.deserialize(json, ManualTask.class);
            case QUEST:
                return context.deserialize(json, QuestTask.class);
            case SKILL_LEVEL:
                return context.deserialize(json, SkillLevelTask.class);
            default:
                log.error("Unknown task type: " + taskType);
                return null;
        }
    }
}