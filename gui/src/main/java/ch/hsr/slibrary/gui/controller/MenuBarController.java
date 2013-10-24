package ch.hsr.slibrary.gui.controller;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 22.10.13
 * Time: 15:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class MenuBarController {


    private JMenuBar menuBar;


    public MenuBarController(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }


    public JMenuBar getMenuBar() {
        return  menuBar;
    }



}
