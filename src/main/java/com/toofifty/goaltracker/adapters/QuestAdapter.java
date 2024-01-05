package com.toofifty.goaltracker.adapters;

import com.google.gson.*;
import net.runelite.api.Quest;

import java.lang.reflect.Type;
import java.util.Arrays;

public class QuestAdapter implements JsonSerializer<Quest>, JsonDeserializer<Quest> {

    @Override
    public JsonElement serialize(Quest src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.getId());
    }

    @Override
    public Quest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException  {
        return Arrays.stream(Quest.values()).filter((quest) -> quest.getId() == json.getAsInt()).findFirst().orElse(null);
    }
}