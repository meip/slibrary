package ch.hsr.slibrary.gui.validation;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class IsIntRangeValidation implements Validation {
    private JTextComponent jTextComponent;
    private String componentLabel;
    private int minRange;
    private int maxRange;

    public IsIntRangeValidation(JTextComponent jTextComponent, int minRange, int maxRange) {
        this.jTextComponent = jTextComponent;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    public IsIntRangeValidation(JTextComponent jTextComponent, int minRange, int maxRange, String componentLabel) {
        this(jTextComponent, minRange, maxRange);
        this.componentLabel = componentLabel;
    }

    @Override
    public boolean isValid() {
        try {
            Integer myInt = Integer.valueOf(jTextComponent.getText());
            return (myInt > minRange && myInt < maxRange);
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    @Override
    public void onError() {
        jTextComponent.setBorder(new ErrorBorder(Color.RED, 2, new ImageIcon(getClass().getClassLoader().getResource("error_10x10.png"))));
    }

    @Override
    public void onSuccess() {
        UIDefaults uidefs = UIManager.getLookAndFeelDefaults();
        jTextComponent.setBackground(uidefs.getColor("TextField.background"));
        jTextComponent.setBorder(uidefs.getBorder("TextField.border"));
    }
}
