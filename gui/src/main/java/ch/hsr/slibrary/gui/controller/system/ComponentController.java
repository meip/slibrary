package ch.hsr.slibrary.gui.controller.system;

import ch.hsr.slibrary.gui.form.GUIComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class ComponentController {
    protected GUIComponent component;
    private ComponentControllerDelegate delegate;
    private String title = "";
    protected boolean isInSaveProgress = false;

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

    protected void bindKeyStrokes() {
        component.getContainer().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                "ESCAPECOMPONENT");
        component.getContainer().getActionMap().put("ESCAPECOMPONENT", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                escapeComponent();
            }
        });
    }

    public void escapeComponent() {
        return;
    }

    public void setFocus() {
        this.component.getContainer().requestFocusInWindow();
    }
}
