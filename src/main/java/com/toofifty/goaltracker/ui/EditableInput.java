package com.toofifty.goaltracker.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

public class EditableInput extends JPanel
{

    private static final Border INPUT_BOTTOM_BORDER = new CompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 1, 0, ColorScheme.DARK_GRAY_COLOR),
        BorderFactory.createLineBorder(ColorScheme.DARKER_GRAY_COLOR)
    );

    private final FlatTextField inputField = new FlatTextField();
    private final TextButton save = new TextButton("Save").narrow();
    private final TextButton cancel = new TextButton("Cancel", ColorScheme.PROGRESS_ERROR_COLOR)
        .narrow();
    private final TextButton edit = new TextButton("Edit", ColorScheme.LIGHT_GRAY_COLOR).narrow();

    private Consumer<String> saveAction;

    private String localValue = "";

    EditableInput(Consumer<String> saveAction)
    {
        this.saveAction = saveAction;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(INPUT_BOTTOM_BORDER);

        JPanel actions = new JPanel(new BorderLayout(3, 0));
        actions.setBorder(new EmptyBorder(0, 0, 0, 8));
        actions.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        save.setVisible(false);
        save.onClick(e -> this.save());

        cancel.setVisible(false);
        cancel.onClick(e -> this.cancel());

        edit.onClick(e -> {
            inputField.setEditable(true);
            updateActions(true);
        });

        actions.add(save, BorderLayout.EAST);
        actions.add(cancel, BorderLayout.WEST);
        actions.add(edit, BorderLayout.CENTER);

        inputField.setText(localValue);
        inputField.setBorder(null);
        inputField.setEditable(false);
        inputField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        inputField.setPreferredSize(new Dimension(50, 24));
        inputField.getTextField().setForeground(Color.WHITE);
        inputField.getTextField().setBorder(new EmptyBorder(0, 8, 0, 0));
        inputField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
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

    private void save()
    {
        localValue = inputField.getText();
        saveAction.accept(localValue);

        inputField.setEditable(false);
        updateActions(false);
        requestFocusInWindow();
    }

    private void cancel()
    {
        inputField.setEditable(false);
        inputField.setText(localValue);

        updateActions(false);
        requestFocusInWindow();
    }

    private void updateActions(boolean saveAndCancel)
    {
        save.setVisible(saveAndCancel);
        cancel.setVisible(saveAndCancel);
        edit.setVisible(!saveAndCancel);

        if (saveAndCancel) {
            inputField.getTextField().requestFocusInWindow();
            inputField.getTextField().selectAll();
        }
    }

    public void setValue(String value)
    {
        localValue = value;

        if (localValue == null) {
            localValue = "";
        }

        inputField.setText(localValue);
    }

    @Override
    public synchronized void addMouseListener(MouseListener l)
    {
        inputField.getTextField().addMouseListener(l);
    }
}
