package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewLoanDetailController extends LoanDetailController {
    private boolean isNewLoan = true;

    public NewLoanDetailController(String title, LoanDetail component, Library library) {
        super(title, component, new Loan(null, null), library);
        initializeSaveButton();
    }

    public void initializeSaveButton() {
        final LoanDetailController self = this;
        loanDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValid()) {
                    saveChanges();
                    if (getDelegate() != null) getDelegate().detailControllerDidSave(self, true);
                }
            }
        });
    }

    public void saveChanges() {
        library.createAndAddLoan((Customer) loanDetail.getCustomerSelect().getSelectedItem(), (Copy) loanDetail.getCopySelect().getSelectedItem());
    }

}
