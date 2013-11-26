package ch.hsr.slibrary.gui.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 25.11.13
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public class TableHelper {
    public static void setAlternatingRowStyle(JTable table) {
        table.setRowHeight(30);
        table.setDefaultRenderer(Object.class, new AlternateTableRowRenderer());
        table.setDefaultRenderer(Icon.class, new IconAlternateTableRowRenderer());
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(false);
        table.setGridColor(Color.lightGray);
    }
}
