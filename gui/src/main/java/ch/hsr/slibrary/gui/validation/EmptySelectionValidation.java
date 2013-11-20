package ch.hsr.slibrary.gui.validation;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public abstract class EmptySelectionValidation implements Validation {
    private JComponent jComponent;
    private String componentLabel;

    public EmptySelectionValidation(JComponent jComponent) {
        this.jComponent = jComponent;
    }

    public EmptySelectionValidation(JComponent jComponent, String componentLabel) {
        this(jComponent);
        this.componentLabel = componentLabel;
    }

    @Override
    public void onError() {
        jComponent.setBorder(new ErrorBorder(Color.RED, 2, new ImageIcon(getClass().getClassLoader().getResource("error_10x10.png"))));
    }

    @Override
    public void onSuccess() {
        UIDefaults uidefs = UIManager.getLookAndFeelDefaults();
        jComponent.setBackground(uidefs.getColor("TextField.background"));
        jComponent.setBorder(uidefs.getBorder("TextField.border"));
    }
}
