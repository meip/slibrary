package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class LoanDetail extends GUIComponent {
    private JTextField customerIdentityField;
    private JComboBox customerSelect;
    private JTextField returnDateField;
    private JLabel amountLabel;
    private JTable exemplarTable;
    private JTextField exemplarIdField;
    private JButton addLoanButton;
    private JLabel exemplarIdValidationLabel;
    private JPanel loanDetailPanel;

    public LoanDetail() {
        this.container = loanDetailPanel;
    }

    public JTextField getCustomerIdentityField() {
        return customerIdentityField;
    }

    public void setCustomerIdentityField(JTextField customerIdentityField) {
        this.customerIdentityField = customerIdentityField;
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

    public JTable getExemplarTable() {
        return exemplarTable;
    }

    public void setExemplarTable(JTable exemplarTable) {
        this.exemplarTable = exemplarTable;
    }

    public JTextField getExemplarIdField() {
        return exemplarIdField;
    }

    public void setExemplarIdField(JTextField exemplarIdField) {
        this.exemplarIdField = exemplarIdField;
    }

    public JButton getAddLoanButton() {
        return addLoanButton;
    }

    public void setAddLoanButton(JButton addLoanButton) {
        this.addLoanButton = addLoanButton;
    }

    public JLabel getExemplarIdValidationLabel() {
        return exemplarIdValidationLabel;
    }

    public void setExemplarIdValidationLabel(JLabel exemplarIdValidationLabel) {
        this.exemplarIdValidationLabel = exemplarIdValidationLabel;
    }

    public JPanel getLoanDetailPanel() {
        return loanDetailPanel;
    }

    public void setLoanDetailPanel(JPanel loanDetailPanel) {
        this.loanDetailPanel = loanDetailPanel;
    }
}
