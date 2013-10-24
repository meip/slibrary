package ch.hsr.slibrary.gui.controller;

public interface BookDetailControllerDelegate extends  ComponentControllerDelegate{
    public void detailControllerDidCancel(BookDetailController bookDetailController);
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose);
}
