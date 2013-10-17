package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.GUIComponent;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class ComponentController {
    protected GUIComponent component;

    public WindowController getWindowController() {
        return windowController;
    }

    public void setWindowController(WindowController windowController) {
        this.windowController = windowController;
    }

    protected WindowController windowController;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title = "";

    public GUIComponent getComponent() {
        return this.component;
    }

    public ComponentController(String title) {
        setTitle(title);
    }
}
