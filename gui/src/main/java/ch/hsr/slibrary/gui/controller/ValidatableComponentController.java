package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.validation.ValidationRule;

import java.util.LinkedList;
import java.util.List;

public abstract class ValidatableComponentController extends ComponentController {
    protected List<ValidationRule> validationRules = new LinkedList<>();

    protected ValidatableComponentController(String title) {
        super(title);
    }

    public abstract void setValidations();

    public boolean isValid() {
        boolean isValid = true;
        for (ValidationRule vr : validationRules) {
            if (!vr.validate()) {
                isValid = false;
            }
        }
        return isValid;
    }
}
