package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.ReorderableList;
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class ListItemPanel<T> extends JPanel {
    private Runnable update;

    ListItemPanel(ReorderableList<T> list, T item) {
        super(new BorderLayout());

        setBorder(new EmptyBorder(8, 8, 8, 8));
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JMenuItem moveUp = new JMenuItem("Move up");
        moveUp.addActionListener(e -> {
            list.move(item, -1);
            update.run();
        });

        JMenuItem moveDown = new JMenuItem("Move down");
        moveDown.addActionListener(e -> {
            list.move(item, 1);
            update.run();
        });

        JMenuItem moveToTop = new JMenuItem("Move to top");
        moveToTop.addActionListener(e -> {
            list.moveToTop(item);
            update.run();
        });

        JMenuItem moveToBottom = new JMenuItem("Move to bottom");
        moveToBottom.addActionListener(e -> {
            list.moveToBottom(item);
            update.run();
        });

        JMenuItem removeTask = new JMenuItem("Remove");
        removeTask.addActionListener(e -> {
            list.remove(item);
            update.run();
        });

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        if (!list.isFirst(item)) popupMenu.add(moveUp);
        if (!list.isLast(item)) popupMenu.add(moveDown);
        if (!list.isFirst(item)) popupMenu.add(moveToTop);
        if (!list.isLast(item)) popupMenu.add(moveToBottom);
        popupMenu.add(removeTask);

        setComponentPopupMenu(popupMenu);
    }

    public ListItemPanel<T> add(Component comp) {
        super.add(comp, BorderLayout.CENTER);
        return this;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        for (Component component : getComponents()) {
            component.setBackground(bg);
        }
    }

    public ListItemPanel<T> onClick(Consumer<MouseEvent> clickListener) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickListener.accept(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(ColorScheme.DARK_GRAY_HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(ColorScheme.DARK_GRAY_COLOR);
            }
        });
        return this;
    }

    public ListItemPanel<T> onUpdate(Runnable update) {
        this.update = update;
        return this;
    }
}
