package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public abstract class GUIComponent extends JComponent {
    protected JComponent container;

    public JComponent getContainer() {
        if(container == null) {
            container = new JLabel("GUIComponent container not set!");
        }
        return container;
    }
}
