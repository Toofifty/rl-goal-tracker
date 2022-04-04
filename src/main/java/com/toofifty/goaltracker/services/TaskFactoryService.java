package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.factory.ItemTaskFactory;
import com.toofifty.goaltracker.goal.factory.ManualTaskFactory;
import com.toofifty.goaltracker.goal.factory.QuestTaskFactory;
import com.toofifty.goaltracker.goal.factory.SkillLevelTaskFactory;
import com.toofifty.goaltracker.goal.factory.SkillXpTaskFactory;
import com.toofifty.goaltracker.goal.factory.TaskFactory;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class TaskFactoryService
{
    private final Map<Class<?>, TaskFactory<? extends Task>> services = new HashMap<>();

    @Inject
    TaskFactoryService(
        ManualTaskFactory manualTaskFactory,
        SkillLevelTaskFactory skillLevelTaskFactory,
        SkillXpTaskFactory skillXpTaskFactory,
        QuestTaskFactory questTaskFactory,
        ItemTaskFactory itemTaskService
    )
    {
        services.put(ManualTaskFactory.class, manualTaskFactory);
        services.put(SkillLevelTaskFactory.class, skillLevelTaskFactory);
        services.put(SkillXpTaskFactory.class, skillXpTaskFactory);
        services.put(QuestTaskFactory.class, questTaskFactory);
        services.put(ItemTaskFactory.class, itemTaskService);
    }

    public TaskFactory<? extends Task> get(Task task)
    {
        return get(task.getFactoryClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends TaskFactory<? extends Task>> T get(Class<T> factoryClass)
    {
        return (T) services.get(factoryClass);
    }
}
