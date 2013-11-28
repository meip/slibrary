package ch.hsr.slibrary.gui.controller.listener;

import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoanTableListener implements ItemListener, KeyListener {
    private Library library;
    private JCheckBox onlyOverdueCheckbox;
    private JCheckBox onlyLentChechBox;
    private JTable loanTable;
    private JTextField searchField;

    private RowFilter<Object, Object> onlyLentFilter;
    private RowFilter<Object, Object> onlyOverdueFilter;
    private RowFilter<Object, Object> regexFilter;

    public LoanTableListener(Library library, JCheckBox onlyOverdueCheckbox, JCheckBox onlyLentChechBox, JTextField searchField, JTable loanTable) {
        this.library = library;
        this.onlyOverdueCheckbox = onlyOverdueCheckbox;
        this.onlyLentChechBox = onlyLentChechBox;
        this.loanTable = loanTable;
        this.searchField = searchField;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        filterTable();
    }

    private void filterTable() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(loanTable.getModel());
        loanTable.setRowSorter(sorter);

        onlyLentFilter = new RowFilter<Object, Object>() {
            public boolean include(Entry entry) {
                ImageIcon statusIcon = (ImageIcon) entry.getValue(0);
                String daysOverdueStatus = statusIcon.getDescription();
                int i = (int) entry.getIdentifier();
                return library.getLoans().get(i).isLent();
            }
        };

        onlyOverdueFilter = new RowFilter<Object, Object>() {
            public boolean include(Entry entry) {
                ImageIcon statusIcon = (ImageIcon) entry.getValue(0);
                String daysOverdueStatus = statusIcon.getDescription();
                int i = (int) entry.getIdentifier();
                return !daysOverdueStatus.equals(LoanUtil.LOAN_IS_VALID);
            }
        };

        regexFilter = RowFilter.regexFilter("(?i)" + searchField.getText());

        List<RowFilter<Object, Object>> allFilters = new LinkedList<>();


        if(onlyLentChechBox.isSelected()) {
            allFilters.add(onlyLentFilter);
        }
        if(onlyOverdueCheckbox.isSelected()) {
            allFilters.add(onlyOverdueFilter);
        }
        allFilters.add(regexFilter);

        sorter.setRowFilter(RowFilter.andFilter(allFilters));
        loanTable.setRowSorter(sorter);
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
}
