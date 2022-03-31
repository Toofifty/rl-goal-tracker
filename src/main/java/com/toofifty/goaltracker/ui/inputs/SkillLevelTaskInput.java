package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.factory.SkillLevelTaskFactory;
import com.toofifty.goaltracker.ui.ComboBox;
import com.toofifty.goaltracker.ui.SimpleDocumentListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

public class SkillLevelTaskInput extends TaskInput
{
    private Goal goal;

    private FlatTextField levelField;
    private String levelFieldValue = "99";

    private ComboBox<Skill> skillField;

    private Pattern numberPattern = Pattern.compile("^(?:\\d{1,2})?$");

    public SkillLevelTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, "Skill level");
        this.goal = goal;

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
    protected void onSubmit()
    {
        if (levelField.getText().isEmpty()) {
            return;
        }

        SkillLevelTask task = new SkillLevelTaskFactory(plugin, goal).create(
            (Skill) skillField.getSelectedItem(),
            Integer.parseInt(levelField.getText())
        );
        goal.add(task);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        levelFieldValue = "99";
        levelField.setText(levelFieldValue);

        skillField.setSelectedIndex(0);
    }
}
