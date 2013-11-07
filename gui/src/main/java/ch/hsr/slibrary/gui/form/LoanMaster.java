package ch.hsr.slibrary.gui.form;

import javax.swing.*;

public class LoanMaster extends GUIComponent {
    private JPanel loanMasterPanel;
    private JLabel loanCountLabel;
    private JLabel currentLoanLabel;
    private JLabel overdueLoanLabel;
    private JTable loanTable;
    private JTextField searchField;
    private JCheckBox onlyOverdueCheckbox;
    private JButton showSelctionButton;
    private JButton newLoanButton;
    private JCheckBox onlyLentCheckbox;

    public LoanMaster() {
        this.container = loanMasterPanel;
    }

    public JPanel getLoanMasterPanel() {
        return loanMasterPanel;
    }

    public void setLoanMasterPanel(JPanel loanMasterPanel) {
        this.loanMasterPanel = loanMasterPanel;
    }

    public JLabel getLoanCountLabel() {
        return loanCountLabel;
    }

    public void setLoanCountLabel(JLabel loanCountLabel) {
        this.loanCountLabel = loanCountLabel;
    }

    public JLabel getCurrentLoanLabel() {
        return currentLoanLabel;
    }

    public void setCurrentLoanLabel(JLabel currentLoanLabel) {
        this.currentLoanLabel = currentLoanLabel;
    }

    public JLabel getOverdueLoanLabel() {
        return overdueLoanLabel;
    }

    public void setOverdueLoanLabel(JLabel overdueLoanLabel) {
        this.overdueLoanLabel = overdueLoanLabel;
    }

    public JTable getLoanTable() {
        return loanTable;
    }

    public void setLoanTable(JTable loanTable) {
        this.loanTable = loanTable;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public JCheckBox getOnlyOverdueCheckbox() {
        return onlyOverdueCheckbox;
    }

    public void setOnlyOverdueCheckbox(JCheckBox onlyOverdueCheckbox) {
        this.onlyOverdueCheckbox = onlyOverdueCheckbox;
    }

    public JButton getShowSelctionButton() {
        return showSelctionButton;
    }

    public void setShowSelctionButton(JButton showSelctionButton) {
        this.showSelctionButton = showSelctionButton;
    }

    public JButton getNewLoanButton() {
        return newLoanButton;
    }

    public void setNewLoanButton(JButton newLoanButton) {
        this.newLoanButton = newLoanButton;
    }

    public JCheckBox getOnlyLentCheckbox() {
        return onlyLentCheckbox;
    }

    public void setOnlyLentCheckbox(JCheckBox onlyLentCheckbox) {
        this.onlyLentCheckbox = onlyLentCheckbox;
    }
}
