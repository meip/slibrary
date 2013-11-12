package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.system.ComponentControllerDelegate;

public interface LoanDetailControllerDelegate extends ComponentControllerDelegate {
    public void detailControllerDidCancel(LoanDetailController loanDetailController);
    public void detailControllerDidSave(LoanDetailController loanDetailController, boolean shouldClose);
}
