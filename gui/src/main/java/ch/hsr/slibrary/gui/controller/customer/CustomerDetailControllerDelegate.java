package ch.hsr.slibrary.gui.controller.customer;

import ch.hsr.slibrary.gui.controller.system.ComponentControllerDelegate;

public interface CustomerDetailControllerDelegate extends ComponentControllerDelegate {
    public void detailControllerDidCancel(CustomerDetailController customerDetailController);

    public void detailControllerDidSave(CustomerDetailController customerDetailController, boolean shouldClose);
}
