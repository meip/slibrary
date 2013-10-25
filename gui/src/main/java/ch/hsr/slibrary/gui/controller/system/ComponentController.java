package ch.hsr.slibrary.gui.controller.system;

import ch.hsr.slibrary.gui.form.GUIComponent;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class ComponentController {
    protected GUIComponent component;
    private ComponentControllerDelegate delegate;
    private String title = "";

    public ComponentController(String title) {
        setTitle(title);
    }

    public void initialize() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if(delegate != null) delegate.controllerDidChangeTitle(this);
    }

    public GUIComponent getComponent() {
        return this.component;
    }

    public ComponentControllerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(ComponentControllerDelegate delegate) {
        this.delegate = delegate;
    }

}
