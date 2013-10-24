package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class Toolbar extends GUIComponent{

    private JButton doubleButton;
    private JButton splitButton;
    private JPanel panel;
    private JButton embeddedButton;
    private JButton separatedButton;

    public Toolbar() {
        this.container = panel;
    }

    public JButton getDoubleButton() {
        return doubleButton;
    }

    public JButton getSplitButton() {
        return splitButton;
    }

}
