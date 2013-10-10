package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.GUIComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class BookMasterController extends ComponentController implements Observer {

    private JTextField textField;

    public BookMasterController(String title, BookMaster component) {
        super(title);
        this.component = component;
        component.getButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });
        textField = component.getTextField1();

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
