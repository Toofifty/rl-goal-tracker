package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.ui.components.TextButton;
import com.toofifty.goaltracker.ui.inputs.ItemTaskInput;
import com.toofifty.goaltracker.ui.inputs.ManualTaskInput;
import com.toofifty.goaltracker.ui.inputs.QuestTaskInput;
import com.toofifty.goaltracker.ui.inputs.SkillLevelTaskInput;
import com.toofifty.goaltracker.ui.inputs.SkillXpTaskInput;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.function.Consumer;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;

public class NewTaskPanel extends JPanel
{
    private final TextButton moreOptionsButton;
    private final GoalTrackerPlugin plugin;
    private final Goal goal;

    private JPanel moreOptionsPanel;
    private Consumer<Task> listener;

    NewTaskPanel(GoalTrackerPlugin plugin, Goal goal)
    {
        super();
        this.plugin = plugin;
        this.goal = goal;

        setLayout(new GridBagLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;
        constraints.ipady = 8;

        add(new ManualTaskInput(plugin, goal).onSubmit((task) -> this.listener.accept(task)), constraints);
        constraints.gridy++;

        moreOptionsButton = new TextButton("+ More options");
        moreOptionsButton.setBorder(new EmptyBorder(4, 8, 0, 8));
        moreOptionsButton.onClick(e -> {
            if (moreOptionsPanel.isVisible()) {
                hideMoreOptions();
            } else {
                showMoreOptions();
            }
        });
        JPanel moreOptionsButtonPanel = new JPanel(new BorderLayout());
        moreOptionsButtonPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        moreOptionsButtonPanel.add(moreOptionsButton, BorderLayout.WEST);

        add(moreOptionsButtonPanel, constraints);
        constraints.gridy++;

        createMoreOptionsPanel();
        add(moreOptionsPanel, constraints);
    }

    private void hideMoreOptions()
    {
        moreOptionsButton.setText("+ More options");
        moreOptionsButton.setMainColor(ColorScheme.PROGRESS_COMPLETE_COLOR);

        moreOptionsPanel.setVisible(false);
    }

    private void showMoreOptions()
    {
        moreOptionsButton.setText("- More options");
        moreOptionsButton.setMainColor(ColorScheme.PROGRESS_ERROR_COLOR);

        moreOptionsPanel.setVisible(true);
    }

    private void createMoreOptionsPanel()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;
        constraints.ipady = 8;

        moreOptionsPanel = new JPanel(new GridBagLayout());
        moreOptionsPanel.setVisible(false);
        moreOptionsPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);

        moreOptionsPanel.add(new SkillLevelTaskInput(plugin, goal).onSubmit((task) -> this.listener.accept(task)), constraints);
        constraints.gridy++;

        moreOptionsPanel.add(new SkillXpTaskInput(plugin, goal).onSubmit((task) -> this.listener.accept(task)), constraints);
        constraints.gridy++;

        moreOptionsPanel.add(new QuestTaskInput(plugin, goal).onSubmit((task) -> this.listener.accept(task)), constraints);
        constraints.gridy++;

        moreOptionsPanel.add(new ItemTaskInput(plugin, goal).onSubmit((task) -> this.listener.accept(task)), constraints);
        constraints.gridy++;
    }

    public void onTaskAdded(Consumer<Task> listener)
    {
        this.listener = listener;
    }
}
