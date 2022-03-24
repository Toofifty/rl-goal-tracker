package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.ui.inputs.ManualTaskInput;
import com.toofifty.goaltracker.ui.inputs.QuestTaskInput;
import com.toofifty.goaltracker.ui.inputs.SkillLevelTaskInput;
import com.toofifty.goaltracker.ui.inputs.SkillXpTaskInput;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewTaskPanel extends JPanel
{
    private Runnable updater;
    private TextButton moreOptionsButton;
    private JPanel moreOptionsPanel;

    private Goal goal;

    NewTaskPanel(Goal goal)
    {
        super();
        this.goal = goal;

        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;
        constraints.ipady = 8;

        add(new ManualTaskInput(goal).onUpdate(() -> updater.run()), constraints);
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

        moreOptionsPanel.add(new SkillLevelTaskInput(goal).onUpdate(() -> updater.run()), constraints);
        constraints.gridy++;

        moreOptionsPanel.add(new SkillXpTaskInput(goal).onUpdate(() -> updater.run()), constraints);
        constraints.gridy++;

        moreOptionsPanel.add(new QuestTaskInput(goal).onUpdate(() -> updater.run()), constraints);
        constraints.gridy++;
    }

    public void onUpdate(Runnable updater)
    {
        this.updater = updater;
    }
}
