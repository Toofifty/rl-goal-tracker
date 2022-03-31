package com.toofifty.goaltracker.ui;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.function.Function;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

public class ComboBox<T> extends JComboBox<T>
{
    private Function<T, String> formatter = null;

    public ComboBox(List<T> items)
    {
        this((T[]) items.toArray());
    }

    public ComboBox(T[] items)
    {
        super(items);

        setForeground(Color.WHITE);
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setFocusable(false);
        setRenderer(new ComboBoxListRenderer<>(formatter));
        setUI(new ComboBoxUI());
        setBorder(new EmptyBorder(0, 0, 0, 0));
    }

    public void setFormatter(Function<T, String> formatter)
    {
        this.formatter = formatter;
        setRenderer(new ComboBoxListRenderer<>(formatter));
    }
}

class ComboBoxUI extends BasicComboBoxUI
{
    private static final ImageIcon ARROW_UP;
    private static final ImageIcon ARROW_DOWN;

    static {
        final BufferedImage arrowUp = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/combo_arrow_up.png");
        ARROW_UP = new ImageIcon(arrowUp);

        final BufferedImage arrowDown = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/combo_arrow_down.png");
        ARROW_DOWN = new ImageIcon(arrowDown);
    }

    @Override
    protected JButton createArrowButton()
    {
        JButton button = new JButton();
        button.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        button.setBorder(new EmptyBorder(4, 4, 4, 4));
        button.setBorderPainted(false);
        button.add(new JLabel(ARROW_DOWN));
        return button;
    }
}

class ComboBoxListRenderer<T> implements ListCellRenderer<T>
{
    private Function<T, String> formatter;

    ComboBoxListRenderer(Function<T, String> formatter)
    {
        this.formatter = formatter;
    }

    @Override
    public Component getListCellRendererComponent(
        JList<? extends T> list, T o, int index, boolean isSelected,
        boolean cellHasFocus)
    {
        JPanel container = new JPanel(new BorderLayout());

        container.setBorder(new EmptyBorder(0, 4, 0, 4));

        JLabel label = new JLabel();
        if (formatter != null) {
            label.setText(formatter.apply(o));
        } else {
            label.setText(
                o instanceof Enum ? Text.titleCase((Enum) o) : o.toString());
        }
        container.add(label, BorderLayout.WEST);

        if (isSelected) {
            container.setBackground(ColorScheme.DARK_GRAY_COLOR);
            label.setForeground(Color.WHITE);
        }

        return container;
    }
}
