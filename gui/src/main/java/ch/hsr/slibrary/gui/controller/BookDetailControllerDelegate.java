package ch.hsr.slibrary.gui.controller;

public interface BookDetailControllerDelegate {
    public void detailControllerDidCancel(BookDetailController bookDetailController);
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose);
}
