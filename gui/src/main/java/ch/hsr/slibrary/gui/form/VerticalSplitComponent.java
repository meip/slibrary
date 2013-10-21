package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class VerticalSplitComponent extends GUIComponent {

    private JPanel panel;
    private JSplitPane splitPane;

    public VerticalSplitComponent() {
        this.container = panel;
    }

    public JSplitPane getSplitPane() {
        return splitPane;
    }
}
