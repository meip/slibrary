package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.controller.system.ComponentControllerDelegate;

public interface BookDetailControllerDelegate extends ComponentControllerDelegate {
    public void detailControllerDidCancel(BookDetailController bookDetailController);
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose);
}
