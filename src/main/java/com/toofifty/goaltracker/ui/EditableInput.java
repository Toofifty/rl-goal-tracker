package com.toofifty.goaltracker.ui;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class EditableInput extends JPanel {

    private static final Border INPUT_BOTTOM_BORDER = new CompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, ColorScheme.DARK_GRAY_COLOR),
            BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_COLOR));

    private final FlatTextField inputField = new FlatTextField();
    private final JLabel save = new JLabel("Save");
    private final JLabel cancel = new JLabel("Cancel");
    private final JLabel rename = new JLabel("Edit");

    private Consumer<String> saveAction;

    private String localValue = "";

    EditableInput(Consumer<String> saveAction) {
        this.saveAction = saveAction;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(INPUT_BOTTOM_BORDER);

        JPanel actions = new JPanel(new BorderLayout(3, 0));
        actions.setBorder(new EmptyBorder(0, 0, 0, 8));
        actions.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        save.setVisible(false);
        save.setFont(FontManager.getRunescapeSmallFont());
        save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                save();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                save.setForeground(ColorScheme.PROGRESS_COMPLETE_COLOR);
            }
        });

        cancel.setVisible(false);
        cancel.setFont(FontManager.getRunescapeSmallFont());
        cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                cancel();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                cancel.setForeground(ColorScheme.PROGRESS_ERROR_COLOR);
            }
        });

        rename.setFont(FontManager.getRunescapeSmallFont());
        rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
        rename.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                inputField.setEditable(true);
                updateNameActions(true);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker().darker());
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                rename.setForeground(ColorScheme.LIGHT_GRAY_COLOR.darker());
            }
        });

        actions.add(save, BorderLayout.EAST);
        actions.add(cancel, BorderLayout.WEST);
        actions.add(rename, BorderLayout.CENTER);

        inputField.setText(localValue);
        inputField.setBorder(null);
        inputField.setEditable(false);
        inputField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        inputField.setPreferredSize(new Dimension(50, 24));
        inputField.getTextField().setForeground(Color.WHITE);
        inputField.getTextField().setBorder(new EmptyBorder(0, 8, 0, 0));
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    save();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    cancel();
                }
            }
        });

        add(inputField, BorderLayout.CENTER);
        add(actions, BorderLayout.EAST);
    }

    public void setValue(String value) {
        localValue = value;

        if (localValue == null) {
            localValue = "";
        }

        inputField.setText(localValue);
    }

    private void save() {
        localValue = inputField.getText();
        saveAction.accept(localValue);

        inputField.setEditable(false);
        updateNameActions(false);
        requestFocusInWindow();
    }

    private void cancel() {
        inputField.setEditable(false);
        inputField.setText(localValue);

        updateNameActions(false);
        requestFocusInWindow();
    }

    private void updateNameActions(boolean saveAndCancel) {
        save.setVisible(saveAndCancel);
        cancel.setVisible(saveAndCancel);
        rename.setVisible(!saveAndCancel);

        if (saveAndCancel) {
            inputField.getTextField().requestFocusInWindow();
            inputField.getTextField().selectAll();
        }
    }

    @Override
    public synchronized void addMouseListener(MouseListener l) {
        inputField.getTextField().addMouseListener(l);
    }
}
