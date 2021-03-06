package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class CustomerDetail extends GUIComponent{
    private JTextField firstnameField;
    private JTextField streetField;
    private JTextField surnameField;
    private JTextField cityField;
    private JTextField zipField;
    private JButton cancelButton;
    private JPanel customerDetail;
    private JButton saveButton;
    private JPanel loanListingPanel;

    public CustomerDetail() {
        this.container = customerDetail;
    }

    public JTextField getFirstnameField() {
        return firstnameField;
    }

    public void setFirstnameField(JTextField firstnameField) {
        this.firstnameField = firstnameField;
    }

    public JTextField getStreetField() {
        return streetField;
    }

    public void setStreetField(JTextField streetField) {
        this.streetField = streetField;
    }

    public JTextField getSurnameField() {
        return surnameField;
    }

    public void setSurnameField(JTextField surnameField) {
        this.surnameField = surnameField;
    }

    public JTextField getCityField() {
        return cityField;
    }

    public void setCityField(JTextField cityField) {
        this.cityField = cityField;
    }

    public JTextField getZipField() {
        return zipField;
    }

    public void setZipField(JTextField zipField) {
        this.zipField = zipField;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JPanel getCustomerDetail() {
        return customerDetail;
    }

    public void setCustomerDetail(JPanel customerDetail) {
        this.customerDetail = customerDetail;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JPanel getLoanListingPanel() {
        return loanListingPanel;
    }

    public void setLoanListingPanel(JPanel loanListingPanel) {
        this.loanListingPanel = loanListingPanel;
    }
}
