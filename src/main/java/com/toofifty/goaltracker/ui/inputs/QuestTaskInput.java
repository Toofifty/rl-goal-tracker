package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.ui.ComboBox;
import net.runelite.api.Quest;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class QuestTaskInput extends TaskInput
{
    private Goal goal;

    private ComboBox<Quest> questField;

    public QuestTaskInput(Goal goal)
    {
        super("Quest");
        this.goal = goal;

        List<Quest> quests = Arrays.asList(Quest.values());
        quests.sort(Comparator.comparing(
            (quest) -> quest.getName().replaceFirst("^(A|The) ", "")));
        questField = new ComboBox<>(quests);
        questField.setFormatter(Quest::getName);
        getInputRow().add(questField, BorderLayout.CENTER);
    }

    @Override
    protected void onSubmit()
    {
        QuestTask task = new QuestTask(goal);
        task.setQuest((Quest) questField.getSelectedItem());
        goal.add(task);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        questField.setSelectedIndex(0);
    }
}
