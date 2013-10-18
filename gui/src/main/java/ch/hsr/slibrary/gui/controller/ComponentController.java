package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.GUIComponent;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class ComponentController {
    protected GUIComponent component;
    protected WindowController windowController;
    private String title = "";


    public WindowController getWindowController() {
        return windowController;
    }

    public void initialize() {

    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GUIComponent getComponent() {
        return this.component;
    }

    public ComponentController(String title) {
        setTitle(title);
    }
}
