package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class BookMaster extends GUIComponent {
    private JPanel bookMaster;
    private JButton button1;
    private JTextField textField1;

    public BookMaster() {
        this.container = bookMaster;
    }

    public JPanel getBookMaster() {
        return bookMaster;
    }

    public JButton getButton1() {
        return button1;
    }

    public JTextField getTextField1() {
        return textField1;
    }
}
