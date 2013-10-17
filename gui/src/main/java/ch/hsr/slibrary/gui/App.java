package ch.hsr.slibrary.gui;

import ch.hsr.slibrary.gui.controller.BookMasterController;
import ch.hsr.slibrary.gui.controller.ComponentController;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.controller.TabController;
import ch.hsr.slibrary.gui.form.TabGUIComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class App {
    public static void main(String[] args) {
       /* JFrame frame = new JFrame("BookMasterFrame");

        List<ComponentController> controllers = new ArrayList<>();

        controllers.add(new BookMasterController("Controller 1", new BookMaster()));
        controllers.add(new BookMasterController("Controller 2", new BookMaster()));

        TabController tabController = new TabController(new TabGUIComponent());
        tabController.setComponents(controllers);


        frame.setContentPane(tabController.getComponent().getContainer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);      */
    }
}
