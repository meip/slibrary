package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * User: p1meier
 * Date: 17.10.13
 */
public class BookDetail extends GUIComponent {
    private JTextField isbnField;
    private JTextField authorField;
    private JTextField publisherField;
    private JTextField titleField;
    private JComboBox shelfComboBox;
    private JButton addCopyButton;
    private JLabel copyAmountLabel;
    private JPanel bookDetailPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private JTable copyTable;


    public BookDetail() {
        this.container = bookDetailPanel;

    }

    public JTextField getIsbnField() {
        return isbnField;
    }

    public void setIsbnField(JTextField isbnField) {
        this.isbnField = isbnField;
    }

    public JTextField getAuthorField() {
        return authorField;
    }

    public void setAuthorField(JTextField authorField) {
        this.authorField = authorField;
    }

    public JTextField getPublisherField() {
        return publisherField;
    }

    public void setPublisherField(JTextField publisherField) {
        this.publisherField = publisherField;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public void setTitleField(JTextField titleField) {
        this.titleField = titleField;
    }

    public JComboBox getShelfComboBox() {
        return shelfComboBox;
    }

    public void setShelfComboBox(JComboBox shelfComboBox) {
        this.shelfComboBox = shelfComboBox;
    }

    public JButton getAddCopyButton() {
        return addCopyButton;
    }

    public void setAddCopyButton(JButton addCopyButton) {
        this.addCopyButton = addCopyButton;
    }

    public JTable getCopyTable() {
        return copyTable;
    }


    public JLabel getCopyAmountLabel() {
        return copyAmountLabel;
    }

    public void setCopyAmountLabel(JLabel copyAmountLabel) {
        this.copyAmountLabel = copyAmountLabel;
    }

    public JPanel getBookDetailPanel() {
        return bookDetailPanel;
    }

    public void setBookDetailPanel(JPanel bookDetailPanel) {
        this.bookDetailPanel = bookDetailPanel;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }



}
