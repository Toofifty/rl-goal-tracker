package com.toofifty.goaltracker.adapters;

import com.google.gson.*;
import net.runelite.api.Skill;

import java.lang.reflect.Type;
import java.util.Arrays;

public class SkillAdapter implements JsonSerializer<Skill>, JsonDeserializer<Skill> {
    @Override
    public JsonElement serialize(Skill src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getName());
    }

    @Override
    public Skill deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException  {
        return Arrays.stream(Skill.values()).filter((skill) -> skill.getName().equals(json.getAsString())).findFirst().orElse(null);
    }
}