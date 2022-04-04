package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.factory.SkillXpTaskFactory;
import com.toofifty.goaltracker.ui.SimpleDocumentListener;
import com.toofifty.goaltracker.ui.components.ComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.regex.Pattern;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import net.runelite.api.Skill;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

public class SkillXpTaskInput extends TaskInput
{
    private final FlatTextField xpField;
    private final ComboBox<Skill> skillField;
    private final Pattern numberPattern = Pattern.compile("^(?:\\d+)?$");
    private final Pattern mPattern = Pattern.compile("^(?:\\d+m)?$", Pattern.CASE_INSENSITIVE);
    private final Pattern kPattern = Pattern.compile("^(?:\\d+k)?$", Pattern.CASE_INSENSITIVE);
    private String xpFieldValue = "13034431";

    public SkillXpTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal, "Skill XP");

        xpField = new FlatTextField();
        xpField.setBorder(new EmptyBorder(0, 8, 0, 8));
        xpField.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
        xpField.setText(xpFieldValue);
        xpField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        xpField.getDocument().addDocumentListener(
            (SimpleDocumentListener) e -> SwingUtilities.invokeLater(() -> {
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
        if (xpField.getText().isEmpty()) {
            return;
        }

        addTask(factory(SkillXpTaskFactory.class).create(
            (Skill) skillField.getSelectedItem(),
            Integer.parseInt(xpField.getText())
        ));
    }

    @Override
    protected void reset()
    {
        xpFieldValue = "13034431";
        xpField.setText(xpFieldValue);

        skillField.setSelectedIndex(0);
    }
}
