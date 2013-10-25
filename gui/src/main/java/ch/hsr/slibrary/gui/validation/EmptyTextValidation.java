package ch.hsr.slibrary.gui.validation;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class EmptyTextValidation implements Validation {
    private JTextComponent jTextComponent;
    private String componentLabel;

    public EmptyTextValidation(JTextComponent jTextComponent) {
        this.jTextComponent = jTextComponent;
    }

    public EmptyTextValidation(JTextComponent jTextComponent, String componentLabel) {
        this(jTextComponent);
        this.componentLabel = componentLabel;
    }

    @Override
    public boolean isValid() {
        return jTextComponent.getText().length() > 0;
    }

    @Override
    public void onError() {
        jTextComponent.setBorder(new ErrorBorder(Color.RED, 2, new ImageIcon(getClass().getClassLoader().getResource("error_10x10.png"))));
        jTextComponent.setBackground(Color.ORANGE);
    }

    @Override
    public void onSuccess() {
        UIDefaults uidefs = UIManager.getLookAndFeelDefaults();
        jTextComponent.setBackground(uidefs.getColor("TextField.background"));
        jTextComponent.setBorder(uidefs.getBorder("TextField.border"));
    }
}
