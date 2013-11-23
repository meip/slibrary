package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class LoanListing extends GUIComponent {
    private JPanel loanListing;
    private JTable copyTable;
    private JButton returnSelectedCopyButton;

    public LoanListing() {
        this.container = loanListing;
    }

    public JPanel getLoanListing() {
        return loanListing;
    }

    public void setLoanListing(JPanel loanListing) {
        this.loanListing = loanListing;
    }

    public JTable getCopyTable() {
        return copyTable;
    }

    public void setCopyTable(JTable copyTable) {
        this.copyTable = copyTable;
    }

    public JButton getReturnSelectedCopyButton() {
        return returnSelectedCopyButton;
    }

    public void setReturnSelectedCopyButton(JButton returnSelectedCopyButton) {
        this.returnSelectedCopyButton = returnSelectedCopyButton;
    }
}
