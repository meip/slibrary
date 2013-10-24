package ch.hsr.slibrary.gui.controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ValidatableComponentController extends ComponentController {
    protected Map<JComponent, Validation> validations = new HashMap<>();

    protected ValidatableComponentController(String title) {
        super(title);
    }

    public abstract void setValidations();

    public boolean isValid() {
        UIDefaults uidefs = UIManager.getLookAndFeelDefaults();
        boolean hasValidationErrors = true;
        for (Map.Entry<JComponent, Validation> jcve : validations.entrySet()) {
            if(!jcve.getValue().validate()) {
                hasValidationErrors = false;
                jcve.getKey().setBorder(BorderFactory.createLineBorder(Color.red));
            } else {
                jcve.getKey().setBorder(uidefs.getBorder("TextFieldUI"));
                jcve.getKey().updateUI();
            }
        }
        return hasValidationErrors;
    }
}
