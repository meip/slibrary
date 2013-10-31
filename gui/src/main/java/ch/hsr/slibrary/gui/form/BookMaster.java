package ch.hsr.slibrary.gui.form;

import javax.swing.*;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class BookMaster extends GUIComponent {
    private JPanel bookMaster;
    private JButton addBookButton;
    private JLabel booksAmountLabel;
    private JLabel copyAmountLabel;
    private JLabel numSelectedLabel;
    private JButton displaySelectedButton;
    private JTable table;
    private JTextField searchField;


    public BookMaster() {
        this.container = bookMaster;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getAddBookButton() {
        return addBookButton;
    }

    public JLabel getBooksAmountLabel() {
        return booksAmountLabel;
    }

    public JLabel getCopyAmountLabel() {
        return copyAmountLabel;
    }

    public JLabel getNumSelectedLabel() {
        return numSelectedLabel;
    }

    public JButton getDisplaySelectedButton() {
        return displaySelectedButton;
    }


    public JPanel getBookMaster() {
        return bookMaster;
    }


}
