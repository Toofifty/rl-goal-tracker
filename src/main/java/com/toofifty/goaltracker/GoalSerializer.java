package com.toofifty.goaltracker;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.toofifty.goaltracker.adapters.QuestAdapter;
import com.toofifty.goaltracker.adapters.SkillAdapter;
import com.toofifty.goaltracker.adapters.TaskAdapter;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.utils.ReorderableList;
import net.runelite.api.Quest;
import net.runelite.api.Skill;

import java.util.List;

public class GoalSerializer
{

    public ReorderableList<Goal> deserialize(String serialized)
    {
        return ReorderableList.from(this.getBuilder().fromJson(serialized, Goal[].class));
    }

    public String serialize(List<Goal> goals)
    {
        return this.serialize(goals, false);
    }

    public String serialize(List<Goal> goals, boolean prettyPrinting)
    {
        return this.getBuilder(prettyPrinting).toJson(goals);
    }

    private Gson getBuilder() {
        return this.getBuilder(false);
    }

    private Gson getBuilder(boolean prettyPrinting) {
        GsonBuilder builder = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Task.class, new TaskAdapter())
            .registerTypeAdapter(Skill.class, new SkillAdapter())
            .registerTypeAdapter(Quest.class, new QuestAdapter());

        if (prettyPrinting) {
            builder.setPrettyPrinting();
        }

        return builder.create();
    }
}
