package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.TabGUIComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TabController extends ComponentController {
    private List<ComponentController> controllers = new LinkedList<>();
    private JTabbedPane tabbedPane;

    public TabController(String title, TabGUIComponent component) {
        super(title);
        this.component = component;
        tabbedPane = component.getTabbedPane();
    }

    public TabController(TabGUIComponent component) {
        this("TabController", component);
    }


    public List<ComponentController> getControllers() {
        return controllers;
    }

    public void setControllers(List<ComponentController> controllers) {
        this.controllers = controllers;
        tabbedPane.removeAll();
        for(ComponentController comp: controllers) {
            tabbedPane.addTab(comp.getTitle(), comp.getComponent().getContainer());
        }
    }


    public void addController(ComponentController controller) {
        if(!controllers.contains(controller)) {
            controllers.add(controller);
            if(controllers.size() == 1) {
                setControllers(controllers);
            } else {
                tabbedPane.addTab(controller.getTitle(), controller.getComponent().getContainer());
            }
        }
    }

    public void removeController(ComponentController controller) {
        if(controllers.contains(controller)) {
            controllers.remove(controller);
            tabbedPane.remove(controller.getComponent().getContainer());
        }
    }

    public void removeAllControllers() {
        setControllers(new LinkedList<ComponentController>());
    }

    public boolean containsController(ComponentController controller) {
        return getControllers().contains(controller);
    }

    public void showController(ComponentController controller) {
        if(containsController(controller)) {
            tabbedPane.setSelectedComponent(controller.getComponent().getContainer());
        }
    }



}
