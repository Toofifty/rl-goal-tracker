package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.ReorderableList;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListPanel<T> extends JScrollPane {
    private JPanel wrapperPanel = new JPanel(new BorderLayout());
    private JPanel listPanel = new JPanel(new GridBagLayout());

    private ReorderableList<T> list;
    private Function<T, ListItemPanel<T>> renderItem;

    private int gap = 2;
    private String placeholder = "Nothing interesting happens.";

    ListPanel(ReorderableList<T> list, Function<T, ListItemPanel<T>> renderItem) {
        super();
        this.list = list;
        this.renderItem = renderItem;

        setBorder(new EmptyBorder(10, 10, 10, 10));

        listPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        wrapperPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        wrapperPanel.add(listPanel);

        setBorder(new EmptyBorder(4, 4, 4 - gap, 4));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        getVerticalScrollBar().setBorder(new EmptyBorder(0, 4, 0, 0));

        setViewportView(wrapperPanel);

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        rebuild();
    }

    public void setGap(int gap) {
        this.gap = gap;
        setBorder(new EmptyBorder(4, 4, 4 - gap, 4));
        rebuild();
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        rebuild();
    }

    public void rebuild() {
        listPanel.removeAll();

        List<ListItemPanel> itemPanels = generateItemPanels();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, gap, 0);

        if (itemPanels.isEmpty()) {
            JPanel placeholderPanel = new JPanel();
            placeholderPanel.add(new JLabel(placeholder));
            placeholderPanel.setForeground(ColorScheme.MEDIUM_GRAY_COLOR.darker());
            placeholderPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            listPanel.add(placeholderPanel, constraints);
        } else {
            itemPanels.forEach(component -> {
                listPanel.add(component, constraints);
                component.onUpdate(this::rebuild);
                constraints.gridy++;
            });
        }

        if (getParent() != null) {
            getParent().revalidate();
        }

        revalidate();
    }

    private List<ListItemPanel> generateItemPanels() {
        return list.getAll()
                .stream().map(renderItem::apply)
                .collect(Collectors.toList());
    }
}
