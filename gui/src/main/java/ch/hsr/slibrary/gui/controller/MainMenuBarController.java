package ch.hsr.slibrary.gui.controller;

import javax.swing.*;

public class MainMenuBarController extends MenuBarController {
    public MainMenuBarController(JMenuBar menuBar) {
        super(menuBar);
        initialize();
    }

    private void initialize() {

        JMenu menu = new JMenu("Darstellung");
        getMenuBar().add(menu);

        ButtonGroup group = new ButtonGroup();

        JRadioButtonMenuItem viewItem = new JRadioButtonMenuItem("Als ein Fenster");
        group.add(viewItem);
        menu.add(viewItem);

        viewItem = new JRadioButtonMenuItem("Als zwei Fenster");
        group.add(viewItem);
        menu.add(viewItem);



    }
}
