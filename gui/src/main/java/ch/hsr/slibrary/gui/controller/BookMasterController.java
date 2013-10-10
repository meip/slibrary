package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.GUIComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class BookMasterController extends ComponentController {

    public BookMasterController(BookMaster component) {
        this.component = component;

        component.getButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });
    }

}
