package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.SkillXpTask;
import com.toofifty.goaltracker.ui.ComboBox;
import com.toofifty.goaltracker.ui.SimpleDocumentListener;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class SkillXpTaskInput extends TaskInput
{
    private Goal goal;

    private FlatTextField xpField;
    private String xpFieldValue = "13034431";

    private ComboBox<Skill> skillField;

    private Pattern numberPattern = Pattern.compile("^(?:\\d+)?$");
    private Pattern mPattern = Pattern.compile("^(?:\\d+m)?$", 'i');
    private Pattern kPattern = Pattern.compile("^(?:\\d+k)?$", 'i');

    public SkillXpTaskInput(Goal goal)
    {
        super("Skill XP");
        this.goal = goal;

        xpField = new FlatTextField();
        xpField.setBorder(new EmptyBorder(0, 8, 0, 8));
        xpField.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
        xpField.setText(xpFieldValue);
        xpField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        xpField.getDocument().addDocumentListener((SimpleDocumentListener) e -> SwingUtilities.invokeLater(() -> {
            String value = xpField.getText();

            if (mPattern.matcher(value).find()) {
                value = value.replace("m", "000000");
                xpFieldValue = value;
                xpField.setText(xpFieldValue);
            }

            if (kPattern.matcher(value).find()) {
                value = value.replace("k", "000");
                xpFieldValue = value;
                xpField.setText(xpFieldValue);
            }

            if (!numberPattern.matcher(value).find()) {
                xpField.setText(xpFieldValue);
                return;
            }

            if (Integer.parseInt(value) > 200000000) {
                xpField.setText("200000000");
                value = "200000000";
            }

            xpFieldValue = value;
        }));
        xpField.setPreferredSize(new Dimension(92, PREFERRED_INPUT_HEIGHT));

        getInputRow().add(xpField, BorderLayout.CENTER);

        skillField = new ComboBox<>(Skill.values());

        getInputRow().add(skillField, BorderLayout.WEST);
    }

    @Override
    protected void onSubmit()
    {
        if (xpField.getText().isEmpty()) return;

        SkillXpTask task = new SkillXpTask(goal);
        task.setSkill((Skill) skillField.getSelectedItem());
        task.setXp(Integer.parseInt(xpField.getText()));
        goal.add(task);

        getUpdater().run();
        reset();
    }

    @Override
    protected void reset()
    {
        xpFieldValue = "13034431";
        xpField.setText(xpFieldValue);

        skillField.setSelectedIndex(0);
    }
}
