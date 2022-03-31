package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.goal.factory.QuestTaskFactory;
import com.toofifty.goaltracker.ui.ComboBox;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.runelite.api.Quest;

public class QuestTaskInput extends TaskInput
{
    private final Goal goal;

    private final ComboBox<Quest> questField;

    public QuestTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, "Quest");
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
        QuestTask task = new QuestTaskFactory(plugin, goal).create(
            (Quest) questField.getSelectedItem());
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
