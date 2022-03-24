package com.toofifty.goaltracker.ui;

import org.apache.commons.text.WordUtils;

import javax.swing.*;
import java.awt.*;

public class ComboBoxRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(
        JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        return super.getListCellRendererComponent(
            list, WordUtils.capitalize((String) value), index, isSelected, cellHasFocus);
    }


}
