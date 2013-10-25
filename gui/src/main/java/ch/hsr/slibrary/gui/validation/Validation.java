package ch.hsr.slibrary.gui.validation;

public interface Validation {
    public boolean isValid();
    public void onError();
    public void onSuccess();
}
