package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.ComponentController;
import ch.hsr.slibrary.gui.form.TabGUIComponent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 10.10.13
 * Time: 16:23
 * To change this template use File | Settings | File Templates.
 */
public class TabController extends ComponentController{
    private List<ComponentController> components;
    private JTabbedPane tabbedPane;

    public List<ComponentController> getComponents() {
        return components;
    }

    public void setComponents(List<ComponentController> components) {
        this.components = components;
        tabbedPane.removeAll();
        for(ComponentController comp:components) {
            tabbedPane.addTab(comp.getTitle(), comp.getComponent().getContainer());
        }
    }
    public TabController(String title, TabGUIComponent component) {
        super(title);
        components = new ArrayList<>();
        this.component = component;
        tabbedPane = component.getTabbedPane();
    }

    public TabController(TabGUIComponent component) {
        this("TabController", component);
    }

}
