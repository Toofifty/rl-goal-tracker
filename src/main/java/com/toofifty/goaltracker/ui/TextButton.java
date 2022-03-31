package com.toofifty.goaltracker.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;

public class TextButton extends JLabel
{
    private Color mainColor = ColorScheme.PROGRESS_COMPLETE_COLOR;

    public TextButton(String text, Color mainColor)
    {
        this(text);
        setMainColor(mainColor);
    }

    public TextButton(String text)
    {
        super(text);

        setFont(FontManager.getRunescapeSmallFont());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setBorder(new EmptyBorder(0, 8, 0, 8));
        setForeground(mainColor);
    }

    public TextButton(String text, Consumer<MouseEvent> clickListener)
    {
        this(text);
        onClick(clickListener);
    }

    public TextButton setMainColor(Color mainColor)
    {
        this.mainColor = mainColor;
        setForeground(mainColor);
        return this;
    }

    public TextButton onClick(Consumer<MouseEvent> clickListener)
    {
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                clickListener.accept(e);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                setForeground(mainColor.darker());
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                setForeground(mainColor);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        return this;
    }

    public TextButton narrow()
    {
        setBorder(new EmptyBorder(0, 2, 0, 2));
        return this;
    }
}
