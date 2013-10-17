package ch.hsr.slibrary.gui.form;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 10.10.13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class TabGUIComponent extends GUIComponent {
    private JPanel panel1;

    public JTabbedPane getTabbedPane() {
        return tabbedPane1;
    }

    private JTabbedPane tabbedPane1;

    public TabGUIComponent() {
        this.container = panel1;
    }

}
