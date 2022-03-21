package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.goal.GoalSet;
import net.runelite.client.plugins.screenmarkers.ScreenMarkerPlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.util.ImageUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class GoalTrackerPanel extends PluginPanel {
    private static final ImageIcon ADD_ICON;
    private static final ImageIcon ADD_HOVER_ICON;

    static {
        final BufferedImage addIcon = ImageUtil.loadImageResource(ScreenMarkerPlugin.class, "add_icon.png");
        ADD_ICON = new ImageIcon(addIcon);
        ADD_HOVER_ICON = new ImageIcon(ImageUtil.alphaOffset(addIcon, 0.53f));
    }

    private final JLabel addMarker = new JLabel(ADD_ICON);

    private List<GoalSet> goalSets;

    private JPanel goalSetListPanel = new JPanel();
    private JPanel goalSetListItemsPanel = new JPanel();

    public GoalTrackerPanel(List<GoalSet> goalSets) {
        super();
        this.goalSets = goalSets;

        goalSetListPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        goalSetListPanel.setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        titlePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        titlePanel.setLayout(new BorderLayout());

        addMarker.setToolTipText("Add new goal set");
        addMarker.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                createNewGoalSet(goalSets);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                addMarker.setIcon(ADD_HOVER_ICON);
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                addMarker.setIcon(ADD_ICON);
            }
        });
        titlePanel.add(addMarker, BorderLayout.EAST);

        JLabel title = new JLabel();
        title.setText("Goal Tracker");
        title.setForeground(Color.WHITE);
        title.setFont(FontManager.getRunescapeBoldFont());
        titlePanel.add(title, BorderLayout.WEST);
        goalSetListPanel.add(titlePanel);

        goalSetListItemsPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
        goalSetListItemsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        goalSetListItemsPanel.setLayout(new GridBagLayout());
        goalSetListPanel.add(goalSetListItemsPanel, BorderLayout.SOUTH);

        add(goalSetListPanel);

        redrawGoalSets();
    }

    public void redrawGoalSets() {
        goalSetListItemsPanel.removeAll();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy = 0;
        goalSets.forEach(goalSet -> {
                    goalSetListItemsPanel.add(
                            new GoalSetListItemPanel(goalSet, () -> viewGoalSet(goalSet)),
                            constraints
                    );
                    constraints.gridy++;
                }
        );
    }

    public void viewAllGoalSets() {
        removeAll();
        add(goalSetListPanel);
        redrawGoalSets();
        revalidate();
        repaint();
    }

    public void createNewGoalSet(List<GoalSet> goalSets) {
        GoalSet goalSet = new GoalSet();
        goalSets.add(goalSet);
        viewGoalSet(goalSet);
    }

    public void viewGoalSet(GoalSet goalSet) {
        removeAll();
        add(new GoalSetPanel(this, goalSet));
        revalidate();
        repaint();
    }
}
