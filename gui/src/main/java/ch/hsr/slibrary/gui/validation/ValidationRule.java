package ch.hsr.slibrary.gui.validation;

public class ValidationRule {
    private Validation validation;

    public ValidationRule(Validation validation) {
        this.validation = validation;
    }

    public boolean validate() {
        if(this.validation.isValid()) {
            this.validation.onSuccess();
        } else {
            this.validation.onError();
        }
        return this.validation.isValid();
    }
}
