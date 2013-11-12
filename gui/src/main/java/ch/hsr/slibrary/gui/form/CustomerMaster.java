package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class CustomerMaster extends GUIComponent {
    private JPanel customerMaster;
    private JTextField searchField;
    private JButton editCustomerButton;
    private JTable customerTable;
    private JButton newcustomerButton;

    public CustomerMaster() {
        this.container = customerMaster;
    }

    public JPanel getCustomerMaster() {
        return customerMaster;
    }

    public void setCustomerMaster(JPanel customerMaster) {
        this.customerMaster = customerMaster;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JButton getEditCustomerButton() {
        return editCustomerButton;
    }

    public void setEditCustomerButton(JButton editCustomerButton) {
        this.editCustomerButton = editCustomerButton;
    }

    public JTable getCustomerTable() {
        return customerTable;
    }

    public void setCustomerTable(JTable customerTable) {
        this.customerTable = customerTable;
    }

    public JButton getNewcustomerButton() {
        return newcustomerButton;
    }

    public void setNewcustomerButton(JButton newcustomerButton) {
        this.newcustomerButton = newcustomerButton;
    }
}
