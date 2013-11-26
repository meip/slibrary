package ch.hsr.slibrary.gui.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 25.11.13
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
public class AlternateTableRowRenderer extends DefaultTableCellRenderer
{
    private Color whiteColor = new Color(254, 254, 254);
    private Color alternateColor = new Color(237, 243, 254);
    private Color selectedColor = new Color(61, 128, 223);

    public Component getTableCellRendererComponent(JTable table,  Object value, boolean selected, boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        Color bg;
        if (!selected)
            bg = (row % 2 == 0 ? alternateColor : whiteColor);
        else
            bg = selectedColor;
        setBackground(bg);

        Color fg;
        if (selected)
            fg = Color.white;
        else
            fg = Color.black;
        setForeground(fg);

        setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        return this;
    }
}