package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.GoalSet;
import net.runelite.client.plugins.screenmarkers.ScreenMarkerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GoalSetPanel extends JPanel {
    private static final ImageIcon CLOSE_ICON;
    private static final ImageIcon CLOSE_HOVER_ICON;

    static {
        final BufferedImage deleteImg = ImageUtil.loadImageResource(ScreenMarkerPlugin.class, "delete_icon.png");
        CLOSE_ICON = new ImageIcon(deleteImg);
        CLOSE_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(deleteImg, -100));
    }

    private EditableInput descriptionInput;
    private JLabel closeButton = new JLabel(CLOSE_ICON);
    private JPanel goalListPanel = new JPanel(new GridBagLayout());
    private GoalTrackerPanel parent;
    private GoalSet goalSet;

    GoalSetPanel(GoalTrackerPanel parent, GoalSet goalSet) {
        super();

        this.parent = parent;
        this.goalSet = goalSet;

        setLayout(new BorderLayout());

        descriptionInput = new EditableInput(goalSet::setDescription);
        descriptionInput.setValue(goalSet.getDescription());

        closeButton.setToolTipText("Close");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                close();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                closeButton.setIcon(CLOSE_HOVER_ICON);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                closeButton.setIcon(CLOSE_ICON);
            }
        });
        closeButton.setBorder(new EmptyBorder(0, 8, 0, 0));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(descriptionInput, BorderLayout.CENTER);
        headerPanel.add(closeButton, BorderLayout.EAST);
        headerPanel.setBorder(new EmptyBorder(0, 0, 8, 0));

        add(headerPanel, BorderLayout.NORTH);

        goalListPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        goalListPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        redrawGoals();
        add(goalListPanel, BorderLayout.CENTER);

        NewGoalPanel newGoalPanel = new NewGoalPanel(goalSet);
        newGoalPanel.onUpdate(this::redrawGoals);
        add(newGoalPanel, BorderLayout.SOUTH);
    }

    private void redrawGoals() {
        goalListPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy = 0;
        goalSet.getGoals().forEach(goal -> {
            GoalListItemPanel goalListItemPanel = new GoalListItemPanel(goal, goalSet);
            goalListItemPanel.onUpdate(this::redrawGoals);
            goalListPanel.add(goalListItemPanel, constraints);
            constraints.gridy++;
        });
        revalidate();
        repaint();
    }

    private void close() {
        parent.viewAllGoalSets();
    }
}
