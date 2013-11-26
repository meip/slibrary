package ch.hsr.slibrary.gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 25.11.13
 * Time: 16:39
 * To change this template use File | Settings | File Templates.
 */
public class IconAlternateTableRowRenderer extends AlternateTableRowRenderer{

    public Component getTableCellRendererComponent(JTable table,  Object value, boolean selected, boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        setIcon((Icon) value);
        setValue("");
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }

}
