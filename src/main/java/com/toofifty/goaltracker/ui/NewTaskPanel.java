package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.Goal;
import com.toofifty.goaltracker.goal.ManualTask;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NewTaskPanel extends JPanel {
    private Runnable update;
    private Goal goal;

    NewTaskPanel(Goal goal) {
        super();
        this.goal = goal;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(8, 0, 8, 0));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;
        constraints.ipady = 8;

        JLabel quickAddTitle = new JLabel("Quick add");
        quickAddTitle.setFont(FontManager.getRunescapeSmallFont());
        quickAddTitle.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        quickAddTitle.setBorder(new EmptyBorder(8, 8, 0, 8));

        JLabel customTaskTitle = new JLabel("Add smart task");
        customTaskTitle.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        customTaskTitle.setFont(FontManager.getRunescapeSmallFont());
        customTaskTitle.setBorder(new EmptyBorder(8, 8, 0, 8));

        add(new JPanel().add(quickAddTitle), constraints);
        constraints.gridy++;

        JPanel quickAddRow = new JPanel(new BorderLayout());
        quickAddRow.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        FlatTextField quickAddDescription = new FlatTextField();
        quickAddDescription.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        quickAddDescription.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER
                        && quickAddDescription.getText().length() > 0) {
                    addManualGoal(quickAddDescription.getText());
                    quickAddDescription.setText("");
                    quickAddDescription.requestFocusInWindow();
                }
            }
        });
        TextButton quickAddButton = new TextButton("Add");
        quickAddButton.onClick(e -> {
            if (quickAddDescription.getText().length() > 0) {
                addManualGoal(quickAddDescription.getText());
                quickAddDescription.setText("");
                quickAddDescription.requestFocusInWindow();
            }
        });
        quickAddRow.add(quickAddDescription, BorderLayout.CENTER);
        quickAddRow.add(quickAddButton, BorderLayout.EAST);

        add(quickAddRow, constraints);
        constraints.gridy++;

        add(customTaskTitle, constraints);
        constraints.gridy++;
    }

    public void onUpdate(Runnable update) {
        this.update = update;
    }

    private void addManualGoal(String description) {
        ManualTask goal = new ManualTask();
        goal.setDescription(description);
        this.goal.add(goal);
        if (update != null) {
            SwingUtilities.invokeLater(() -> update.run());
        }
    }
}
