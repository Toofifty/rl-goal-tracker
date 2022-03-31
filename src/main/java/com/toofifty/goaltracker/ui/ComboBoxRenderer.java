package com.toofifty.goaltracker.ui;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import org.apache.commons.text.WordUtils;

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
