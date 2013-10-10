package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.GUIComponent;

import javax.swing.*;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class ComponentController {
    protected GUIComponent component;

    public GUIComponent getComponent() {
        return this.component;
    }
}
