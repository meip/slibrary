package ch.hsr.slibrary.gui.controller.system;

import ch.hsr.slibrary.gui.form.TabGUIComponent;
import ch.hsr.slibrary.gui.util.StringUtil;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.LinkedList;
import java.util.List;

public class TabController extends ComponentController implements  ComponentControllerDelegate{
    private List<ComponentController> controllers = new LinkedList<>();
    private JTabbedPane tabbedPane;
    private TabControllerDelegate tabDelegate;
    private ChangeListener changeListener;

    public TabController(String title, TabGUIComponent component) {
        super(title);
        final TabController self = this;
        this.component = component;
        changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if(getTabDelegate() != null) getTabDelegate().tabControllerDidSelectController(self, getSelectedController());
            }
        };
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
        tabbedPane.removeChangeListener(changeListener);
        for(ComponentController comp: controllers) {
            tabbedPane.addTab(StringUtil.trimToWordsWithMaxLength(comp.getTitle(), 5, 25), comp.getComponent().getContainer());
            comp.setDelegate(this);
        }
        tabbedPane.addChangeListener(changeListener);
    }


    public void addController(ComponentController controller) {
        tabbedPane.removeChangeListener(changeListener);
        if(!controllers.contains(controller)) {
            controllers.add(controller);
            controller.setDelegate(this);
            if(controllers.size() == 1) {
                setControllers(controllers);
            } else {
                tabbedPane.addTab(StringUtil.trimToWordsWithMaxLength(controller.getTitle(), 5, 25), controller.getComponent().getContainer());
                if(tabDelegate != null) tabDelegate.tabControllerDidAddController(this, controller);
            }
        }
        tabbedPane.addChangeListener(changeListener);
    }

    public void insertControllerAt(ComponentController controller, int index) {
        tabbedPane.removeChangeListener(changeListener);
        if(index >= 0 && index < controllers.size()) {
            controllers.add(index, controller);
            controller.setDelegate(this);
            tabbedPane.insertTab(StringUtil.trimToWordsWithMaxLength(controller.getTitle(), 5, 25), null, controller.getComponent().getContainer(), "", index);
            if(tabDelegate != null) tabDelegate.tabControllerDidAddController(this, controller);
        }
        tabbedPane.addChangeListener(changeListener);
    }

    public void removeController(ComponentController controller) {
        tabbedPane.removeChangeListener(changeListener);
        if(controllers.contains(controller)) {
            controllers.remove(controller);
            tabbedPane.remove(controller.getComponent().getContainer());
            if(tabDelegate != null) tabDelegate.tabControllerDidRemoveController(this, controller);
            controller.setDelegate(null);
        }
        tabbedPane.addChangeListener(changeListener);
    }

    public void removeControllerAt(int index) {
        tabbedPane.removeChangeListener(changeListener);
        if(index >= 0 && index < controllers.size()) {
            ComponentController controller = controllers.get(index);
            controller.setDelegate(null);
            controllers.remove(index);
            tabbedPane.remove(index);
            if(tabDelegate != null) tabDelegate.tabControllerDidRemoveController(this, controller);
        }
        tabbedPane.addChangeListener(changeListener);
    }

    public void removeAllControllers() {
        tabbedPane.removeChangeListener(changeListener);
        for(ComponentController controller : controllers) {
            controller.setDelegate(null);
            if(tabDelegate != null) tabDelegate.tabControllerDidRemoveController(this, controller);
        }
        setControllers(new LinkedList<ComponentController>());
        tabbedPane.addChangeListener(changeListener);
    }

    public boolean containsController(ComponentController controller) {
        return getControllers().contains(controller);
    }

    public void showController(ComponentController controller) {
        if(containsController(controller)) {
            tabbedPane.setSelectedComponent(controller.getComponent().getContainer());
            //if(tabDelegate != null) tabDelegate.tabControllerDidSelectController(this, controller);
        }
    }


    public void showControllerAt(int index) {
        if(index >= 0 && index < controllers.size()) {
            tabbedPane.setSelectedIndex(index);
            System.out.println("show controller at: " + index);
           // if(tabDelegate != null) tabDelegate.tabControllerDidSelectController(this, controllers.get(index));
        }
    }

    @Override
    public void controllerDidChangeTitle(ComponentController controller) {
        tabbedPane.setTitleAt(controllers.indexOf(controller), StringUtil.trimToWordsWithMaxLength(controller.getTitle(), 5, 25));
    }

    public TabControllerDelegate getTabDelegate() {
        return tabDelegate;
    }

    public void setTabDelegate(TabControllerDelegate tabDelegate) {
        this.tabDelegate = tabDelegate;
    }

    public ComponentController getSelectedController() {
       int index = tabbedPane.getSelectedIndex();
       return index >= 0 ? controllers.get(index) : null;
    }

    public ComponentController getControllerAt(int index) {
        ComponentController controller = null;
        if(index >= 0 && index < controllers.size()) {
            controller = controllers.get(index);
        }
        return controller;
    }

    public int indexOfController(ComponentController controller) {
        return controllers.indexOf(controller);
    }
}
