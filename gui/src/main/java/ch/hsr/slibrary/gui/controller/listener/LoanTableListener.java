package ch.hsr.slibrary.gui.controller.listener;

import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoanTableListener implements ItemListener {
    private Library library;
    private JCheckBox onlyOverdueCheckbox;
    private JCheckBox onlyLentChechBox;
    private JTable loanTable;

    public LoanTableListener(Library library, JCheckBox onlyOverdueCheckbox, JCheckBox onlyLentChechBox, JTable loanTable) {
        this.library = library;
        this.onlyOverdueCheckbox = onlyOverdueCheckbox;
        this.onlyLentChechBox = onlyLentChechBox;
        this.loanTable = loanTable;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(loanTable.getModel());
        loanTable.setRowSorter(sorter);

        if (onlyLentChechBox.isSelected() && onlyOverdueCheckbox.isSelected()) {
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                public boolean include(Entry entry) {
                    ImageIcon statusIcon = (ImageIcon) entry.getValue(0);
                    String daysOverdueStatus = statusIcon.getDescription();
                    int i = (int) entry.getIdentifier();
                    return library.getLoans().get(i).isLent() && !daysOverdueStatus.equals(LoanUtil.LOAN_IS_VALID);
                }
            };
            sorter.setRowFilter(filter);
        } else if (onlyLentChechBox.isSelected() && !onlyOverdueCheckbox.isSelected()) {
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                public boolean include(Entry entry) {
                    int i = (int) entry.getIdentifier();
                    return library.getLoans().get(i).isLent();
                }
            };
            sorter.setRowFilter(filter);
        } else if (!onlyLentChechBox.isSelected() && onlyOverdueCheckbox.isSelected()) {
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                public boolean include(Entry entry) {
                    ImageIcon statusIcon = (ImageIcon) entry.getValue(0);
                    String daysOverdueStatus = statusIcon.getDescription();
                    return !daysOverdueStatus.equals(LoanUtil.LOAN_IS_VALID);
                }
            };
            sorter.setRowFilter(filter);
        } else {
            loanTable.setRowSorter(sorter);
        }
    }
}
