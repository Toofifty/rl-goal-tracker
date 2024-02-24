package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.ItemTask;
import com.toofifty.goaltracker.ui.SimpleDocumentListener;
import com.toofifty.goaltracker.ui.components.TextButton;
import net.runelite.api.GameState;
import net.runelite.api.ItemComposition;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.FlatTextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.regex.Pattern;

public class ItemTaskInput extends TaskInput<ItemTask>
{
    private final ItemManager itemManager;
    private final ClientThread clientThread;

    private final FlatTextField quantityField = new FlatTextField();
    private final TextButton searchItemButton = new TextButton("Search...");
    private final JLabel selectedItemLabel = new JLabel();
    private final JPanel selectedItemPanel = new JPanel(new BorderLayout());

    private final Pattern numberPattern = Pattern.compile("^(?:\\d+)?$");
    private final Pattern mPattern = Pattern.compile("^(?:\\d+m)?$", Pattern.CASE_INSENSITIVE);
    private final Pattern kPattern = Pattern.compile("^(?:\\d+k)?$", Pattern.CASE_INSENSITIVE);

    private String quantityFieldValue = "1";
    private ItemComposition selectedItem;

    public ItemTaskInput(GoalTrackerPlugin plugin, Goal goal)
    {
        super(plugin, goal, "Item");
        this.itemManager = plugin.getItemManager();
        this.clientThread = plugin.getClientThread();

        searchItemButton.onClick(e -> {
            if (plugin.getClient().getGameState() != GameState.LOGGED_IN) {
                JOptionPane.showMessageDialog(this,
                    "You must be logged in to choose items",
                    "UwU",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            plugin.getItemSearch()
                .tooltipText("Choose an item")
                .onItemSelected(this::setSelectedItem)
                .build();
        });
        getInputRow().add(searchItemButton, BorderLayout.WEST);

        quantityField.setBorder(new EmptyBorder(0, 8, 0, 8));
        quantityField.getTextField().setHorizontalAlignment(SwingConstants.RIGHT);
        quantityField.setText(quantityFieldValue);
        quantityField.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        quantityField.getDocument().addDocumentListener(
            (SimpleDocumentListener) e -> SwingUtilities.invokeLater(() -> {
                String value = quantityField.getText();

                if (mPattern.matcher(value).find()) {
                    value = value.replace("m", "000000");
                    quantityFieldValue = value;
                    quantityField.setText(quantityFieldValue);
                }

                if (kPattern.matcher(value).find()) {
                    value = value.replace("k", "000");
                    quantityFieldValue = value;
                    quantityField.setText(quantityFieldValue);
                }

                if (!numberPattern.matcher(value).find()) {
                    quantityField.setText(quantityFieldValue);
                    return;
                }

                quantityFieldValue = value;
            }));
        quantityField.setPreferredSize(new Dimension(92, PREFERRED_INPUT_HEIGHT));

        getInputRow().add(quantityField, BorderLayout.CENTER);

        selectedItemPanel.setBorder(new EmptyBorder(0, 8, 0, 8));
        selectedItemPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        selectedItemPanel.add(selectedItemLabel, BorderLayout.CENTER);
        TextButton clearItemButton = new TextButton("X")
                .setMainColor(ColorScheme.PROGRESS_ERROR_COLOR)
                .onClick((e) -> clearSelectedItem());
        selectedItemPanel.add(clearItemButton, BorderLayout.EAST);
    }

    private void setSelectedItem(Integer rawId)
    {
        clientThread.invokeLater(() -> {
            int id = itemManager.canonicalize(rawId);
            selectedItem = itemManager.getItemComposition(id);
            selectedItemLabel.setText(selectedItem.getName());

            getInputRow().remove(searchItemButton);
            getInputRow().add(selectedItemPanel, BorderLayout.WEST);

            revalidate();
            repaint();
        });
    }

    @Override
    protected void submit()
    {
        if (selectedItem == null || quantityField.getText().isEmpty()) {
            return;
        }

        this.addTask(ItemTask.builder()
            .itemId(selectedItem.getId())
            .itemName(selectedItem.getName())
            .quantity(Integer.parseInt(quantityField.getText()))
        .build());
    }

    @Override
    protected void reset()
    {
        clearSelectedItem();
        quantityFieldValue = "1";
        quantityField.setText(quantityFieldValue);
    }

    private void clearSelectedItem()
    {
        selectedItem = null;

        getInputRow().remove(selectedItemPanel);
        getInputRow().add(searchItemButton, BorderLayout.WEST);

        revalidate();
        repaint();
    }
}
