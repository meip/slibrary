package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class LoanDetail extends GUIComponent {
    private JTextField customerIdentityField;
    private JComboBox customerSelect;
    private JTextField returnDateField;
    private JLabel amountLabel;
    private JTable copyTable;
    private JTextField copyIdField;
    private JButton addLoanButton;
    private JLabel copyIdValidationLabel;
    private JPanel loanDetailPanel;
    private JButton cancelButton;
    private JButton saveButton;


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

    public JTable getCopyTable() {
        return copyTable;
    }

    public void setCopyTable(JTable copyTable) {
        this.copyTable = copyTable;
    }

    public JTextField getCopyIdField() {
        return copyIdField;
    }

    public void setCopyIdField(JTextField copyIdField) {
        this.copyIdField = copyIdField;
    }

    public JButton getAddLoanButton() {
        return addLoanButton;
    }

    public void setAddLoanButton(JButton addLoanButton) {
        this.addLoanButton = addLoanButton;
    }

    public JLabel getCopyIdValidationLabel() {
        return copyIdValidationLabel;
    }

    public void setCopyIdValidationLabel(JLabel copyIdValidationLabel) {
        this.copyIdValidationLabel = copyIdValidationLabel;
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
}
