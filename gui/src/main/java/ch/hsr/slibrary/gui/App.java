package ch.hsr.slibrary.gui;

import ch.hsr.slibrary.gui.controller.BookMasterController;
import ch.hsr.slibrary.gui.form.BookMaster;
import javax.swing.*;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BookMasterFrame");
        frame.setContentPane(new BookMasterController(new BookMaster()).getComponent().getContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
