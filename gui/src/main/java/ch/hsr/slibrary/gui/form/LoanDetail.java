package ch.hsr.slibrary.gui.form;

import javax.swing.*;
import java.awt.*;

public class LoanDetail extends GUIComponent {
    private JPanel loanDetailPanel;
    private JComboBox customerSelect;
    private JTextField returnDateField;
    private JLabel amountLabel;
    private JTable copyTable;
    private JButton cancelButton;
    private JButton saveButton;
    private JLabel loansOverdueLabel;
    private JLabel loansCurrentLabel;
    private JComboBox copySelect;
    private JPanel loanListingPanel;
    private JPanel copyPanel;
    private JLabel bookLabel;

    public LoanDetail() {
        this.container = loanDetailPanel;
    }


    public JLabel getBookLabel() {
        return bookLabel;
    }

    public JPanel getCopyPanel() {
        return copyPanel;
    }

    public JComboBox getCustomerSelect() {
        return customerSelect;
    }

    public void setCustomerSelect(JComboBox customerSelect) {
        this.customerSelect = customerSelect;
    }

    public JTextField getReturnDateField() {
        return returnDateField;
    }

    public void setReturnDateField(JTextField returnDateField) {
        this.returnDateField = returnDateField;
    }

    public JLabel getAmountLabel() {
        return amountLabel;
    }

    public void setAmountLabel(JLabel amountLabel) {
        this.amountLabel = amountLabel;
    }

    public JTable getCopyTable() {
        return copyTable;
    }

    public void setCopyTable(JTable copyTable) {
        this.copyTable = copyTable;
    }

    public JComboBox getCopySelect() {
        return copySelect;
    }

    public void setCopySelect(JComboBox copySelect) {
        this.copySelect = copySelect;
    }

    public JPanel getLoanDetailPanel() {
        return loanDetailPanel;
    }

    public void setLoanDetailPanel(JPanel loanDetailPanel) {
        this.loanDetailPanel = loanDetailPanel;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JLabel getLoansOverdueLabel() {
        return loansOverdueLabel;
    }

    public void setLoansOverdueLabel(JLabel loansOverdueLabel) {
        this.loansOverdueLabel = loansOverdueLabel;
    }

    public JLabel getLoansCurrentLabel() {
        return loansCurrentLabel;
    }

    public void setLoansCurrentLabel(JLabel loansCurrentLabel) {
        this.loansCurrentLabel = loansCurrentLabel;
    }

    public JPanel getLoanListingPanel() {
        return loanListingPanel;
    }

    public void setLoanListingPanel(JPanel loanListingPanel) {
        this.loanListingPanel = loanListingPanel;
    }
}
