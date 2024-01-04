package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.SkillLevelTask;
import com.toofifty.goaltracker.ui.SimpleDocumentListener;
import com.toofifty.goaltracker.ui.components.ComboBox;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class SkillLevelTaskInput extends TaskInput<SkillLevelTask>
{

    private FlatTextField levelField;
    private String levelFieldValue = "99";

    private ComboBox<Skill> skillField;

    private Pattern numberPattern = Pattern.compile("^(?:\\d{1,2})?$");

    public SkillLevelTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal, "Skill level");

        levelField = new FlatTextField();
        levelField.setBorder(new EmptyBorder(0, 8, 0, 8));
        levelField.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
        levelField.setText(levelFieldValue);
        levelField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        levelField.getDocument().addDocumentListener(
            (SimpleDocumentListener) e -> SwingUtilities.invokeLater(() -> {
                String value = levelField.getText();
                if (!numberPattern.matcher(value).find()) {
                    levelField.setText(levelFieldValue);
                    return;
                }
                levelFieldValue = value;
            }));
        levelField.setPreferredSize(new Dimension(92, PREFERRED_INPUT_HEIGHT));

        getInputRow().add(levelField, BorderLayout.CENTER);

        skillField = new ComboBox<>(Skill.values());

        getInputRow().add(skillField, BorderLayout.WEST);
    }

    @Override
    protected void submit()
    {
        if (levelField.getText().isEmpty()) {
            return;
        }

        addTask(SkillLevelTask.builder()
            .skill((Skill) skillField.getSelectedItem())
            .level(Integer.parseInt(levelField.getText()))
        .build());
    }

    @Override
    protected void reset()
    {
        levelFieldValue = "99";
        levelField.setText(levelFieldValue);

        skillField.setSelectedIndex(0);
    }
}
