package ch.hsr.slibrary.gui.form;

import javax.swing.*;
import java.awt.*;

public abstract class GUIComponent extends JComponent {

    public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 300;

    protected JComponent container;
    protected Dimension minimumSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);

    public JComponent getContainer() {
        if(container == null) {
            container = new JLabel("GUIComponent container not set!");
        }
        return container;
    }


    public Dimension getMinimumSize() {
        return minimumSize;
    }

    public void setMinimumSize(Dimension minimumSize) {
        this.minimumSize = minimumSize;
    }
}
