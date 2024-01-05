package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.QuestTask;
import com.toofifty.goaltracker.ui.components.ComboBox;
import net.runelite.api.Quest;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QuestTaskInput extends TaskInput<QuestTask>
{
    private final ComboBox<Quest> questField;

    public QuestTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal, "Quest");

        List<Quest> quests = Arrays.asList(Quest.values());
        quests.sort(Comparator.comparing(
            (quest) -> quest.getName().replaceFirst("^(A|The) ", "")));
        questField = new ComboBox<>(quests);
        questField.setFormatter(Quest::getName);
        getInputRow().add(questField, BorderLayout.CENTER);
    }

    @Override
    protected void submit()
    {
        addTask(QuestTask.builder()
                .quest((Quest) questField.getSelectedItem())
        .build());
    }

    @Override
    protected void reset()
    {
        questField.setSelectedIndex(0);
    }
}
