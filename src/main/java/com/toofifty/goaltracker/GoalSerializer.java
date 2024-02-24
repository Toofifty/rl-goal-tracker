package com.toofifty.goaltracker;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.toofifty.goaltracker.adapters.QuestAdapter;
import com.toofifty.goaltracker.adapters.SkillAdapter;
import com.toofifty.goaltracker.adapters.TaskAdapter;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.utils.ReorderableList;
import net.runelite.api.Quest;
import net.runelite.api.Skill;

import java.util.List;

import javax.inject.Inject;

public class GoalSerializer
{	
    @Inject
	private Gson gson;

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
        var builder = gson.newBuilder()
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
