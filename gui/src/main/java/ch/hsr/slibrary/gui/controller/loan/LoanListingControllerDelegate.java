package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.spa.Loan;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 25.11.13
 * Time: 18:48
 * To change this template use File | Settings | File Templates.
 */
public interface LoanListingControllerDelegate {
    void loanListingControllerDidSelectLoans(LoanListingController controller, List<Loan> loans);
}
