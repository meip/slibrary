package ch.hsr.slibrary.gui.controller.listener;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class SearchableTableListener implements KeyListener {
    private JTable jTable;
    private JTextField searchField;

    public SearchableTableListener(JTable jTable, JTextField searchField) {
        this.jTable = jTable;
        this.searchField = searchField;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        filterTable();
    }

    private void filterTable() {
        TableModel tableModel = jTable.getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        jTable.setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchField.getText())));
    }
}
