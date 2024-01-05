package com.toofifty.goaltracker.ui.inputs;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.Task;
import com.toofifty.goaltracker.ui.components.TextButton;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;

public abstract class TaskInput<T extends Task> extends JPanel
{
    protected final int PREFERRED_INPUT_HEIGHT = 16;
    protected GoalTrackerPlugin plugin;
    private final Goal goal;
    @Getter
    private final JPanel inputRow;
    @Getter
    private Consumer<T> listener;

    TaskInput(GoalTrackerPlugin plugin, Goal goal, String title)
    {
        super(new GridBagLayout());
        this.plugin = plugin;
        this.goal = goal;

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 1;
        constraints.gridy = 0;
        constraints.ipady = 8;

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontManager.getRunescapeSmallFont());
        titleLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
        titleLabel.setBorder(new EmptyBorder(2, 8, 0, 8));

        JPanel titleContainer = new JPanel(new BorderLayout());
        titleContainer.add(titleLabel, BorderLayout.WEST);

        add(titleContainer, constraints);
        constraints.gridy++;

        inputRow = new JPanel(new BorderLayout());
        inputRow.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        TextButton addButton = new TextButton("Add");
        addButton.onClick(e -> submit());

        inputRow.add(addButton, BorderLayout.EAST);

        add(inputRow, constraints);
        constraints.gridy++;
    }

    abstract protected void submit();

    public void addTask(T task)
    {
        goal.getTasks().add(task);
        this.listener.accept(task);
        this.reset();
    }

    abstract protected void reset();

    public TaskInput<T> onSubmit(Consumer<T> listener)
    {
        this.listener = listener;
        return this;
    }
}
