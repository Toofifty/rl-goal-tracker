package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.GoalSet;
import com.toofifty.goaltracker.goal.ManualGoal;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewGoalPanel extends JPanel {
    private Runnable update;
    private GoalSet goalSet;

    NewGoalPanel(GoalSet goalSet) {
        super();
        this.goalSet = goalSet;

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

        JLabel customGoalTitle = new JLabel("Add smart goal");
        customGoalTitle.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        customGoalTitle.setFont(FontManager.getRunescapeSmallFont());
        customGoalTitle.setBorder(new EmptyBorder(8, 8, 0, 8));

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
        JLabel quickAddButton = new JLabel("Add");
        quickAddButton.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
        quickAddButton.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        quickAddButton.setFont(FontManager.getRunescapeSmallFont());
        quickAddButton.setToolTipText("You can also press enter");
        quickAddButton.setBorder(new EmptyBorder(0, 8, 0, 8));
        quickAddButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (quickAddDescription.getText().length() > 0) {
                    addManualGoal(quickAddDescription.getText());
                    quickAddDescription.setText("");
                    quickAddDescription.requestFocusInWindow();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                quickAddButton.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                quickAddButton.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
            }
        });
        quickAddRow.add(quickAddDescription, BorderLayout.CENTER);
        quickAddRow.add(quickAddButton, BorderLayout.EAST);

        add(quickAddRow, constraints);
        constraints.gridy++;

        add(customGoalTitle, constraints);
        constraints.gridy++;
    }

    public void onUpdate(Runnable update) {
        this.update = update;
    }

    private void addManualGoal(String description) {
        ManualGoal goal = new ManualGoal();
        goal.setDescription(description);
        goalSet.add(goal);
        if (update != null) {
            SwingUtilities.invokeLater(() -> update.run());
        }
    }
}
