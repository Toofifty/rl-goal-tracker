package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.factory.QuestTaskFactory;
import com.toofifty.goaltracker.ui.components.ComboBox;
import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import net.runelite.api.Quest;

public class QuestTaskInput extends TaskInput
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
    protected void onSubmit()
    {
        addTask(factory(QuestTaskFactory.class).create(
            (Quest) questField.getSelectedItem()
        ));
    }

    @Override
    protected void reset()
    {
        questField.setSelectedIndex(0);
    }
}
