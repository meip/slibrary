package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailControllerDelegate;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.gui.form.LoanMaster;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class LoanMasterController extends ComponentController implements Observer, BookDetailControllerDelegate, MasterDetailControllerDelegate {


    private MasterDetailController masterDetailController;
    private LoanMaster loanMaster;
    private Library library;
    private Map<Loan, ComponentController> loanControllerMap = new HashMap<>();
    private ListSelectionModel listSelectionModel;

    public LoanMasterController(String title, LoanMaster component, Library lib) {
        super(title);
        this.component = component;
        loanMaster = component;
        this.library = lib;
    }

    public MasterDetailController getMasterDetailController() {
        return masterDetailController;
    }

    public void setMasterDetailController(MasterDetailController masterDetailController) {
        this.masterDetailController = masterDetailController;
        masterDetailController.setDelegate(this);
    }

    @Override
    public void initialize() {
        initializeButtonListeners();
        initializeTable();
        initializeOverdueFilter();
        initializeObserving();
        initializeSearchField();
        updateUI();
    }

    private void initializeButtonListeners() {

        final LoanMasterController self = this;

        loanMaster.getShowSelctionButton().setEnabled(false);
        loanMaster.getShowSelctionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : loanMaster.getLoanTable().getSelectedRows()) {
                    index = loanMaster.getLoanTable().convertRowIndexToModel(index);
                    Loan loan = library.getLoans().get(index);
                    if (!loanControllerMap.containsKey(loan)) {
                        LoanDetailController controller = createControllerForLoan(loan);
                        controller.setDelegate(self);
                        controller.setMasterDetailController(self.masterDetailController);
                        loanControllerMap.put(loan, controller);
                    }
                    presentLoan(loan);
                }
            }
        });


        loanMaster.getNewLoanButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (masterDetailController != null) {
                }
            }
        });


    }

    private void initializeTable() {
        loanMaster.getLoanTable().setModel(new AbstractTableModel() {

            private String[] columnNames = {"Status", "Exemplar-ID", "Titel", "Ausgeliehen bis", "Ausgeliehen an"};

            @Override
            public int getRowCount() {
                return library.getLoans().size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Loan loan = library.getLoans().get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return LoanUtil.loanStatus(loan.getDaysOverdue());
                    case 1:
                        return loan.getCopy().getInventoryNumber();
                    case 2:
                        return loan.getCopy().getTitle().getName();
                    case 3:
                        if (loan.getDaysOverdue() > 0) {
                            return new SimpleDateFormat().format(new Long(loan.getDueDate().getTimeInMillis())) + " (seit " + loan.getDaysOverdue() + " Tage überfällig)";
                        } else if (loan.isLent()) {
                            long diffTime = loan.getDueDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                            long daysOverDue = diffTime / (1000 * 60 * 60 * 24);
                            return new SimpleDateFormat().format(new Long(loan.getDueDate().getTimeInMillis())) + " (in " + daysOverDue + " Tagen)";
                        } else {
                            return new SimpleDateFormat().format(new Long(loan.getReturnDate().getTimeInMillis()));
                        }
                    case 4:
                        return loan.getCustomer().getSurname() + " " + loan.getCustomer().getName();
                    default:
                        return "";
                }
            }
        });
        listSelectionModel = loanMaster.getLoanTable().getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateLabels();
                updateButtons();
            }
        });
    }

    private void initializeObserving() {
        for (Loan loan : library.getLoans()) {
            loan.addObserver(this);
        }
    }

    private void initializeOverdueFilter() {
        loanMaster.getOnlyOverdueCheckbox().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (loanMaster.getOnlyOverdueCheckbox().isSelected()) {
                    TableModel tableModel = loanMaster.getLoanTable().getModel();
                    TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
                    loanMaster.getLoanTable().setRowSorter(sorter);

                    RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                        public boolean include(Entry entry) {
                            String daysOverdueStatus = (String) entry.getValue(0);
                            return !daysOverdueStatus.equals(LoanUtil.LOAN_IS_VALID);
                        }
                    };
                    sorter.setRowFilter(filter);
                } else {
                    TableModel tableModel = loanMaster.getLoanTable().getModel();
                    TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
                    loanMaster.getLoanTable().setRowSorter(sorter);
                }
            }
        });
    }

    private void initializeSearchField() {
        loanMaster.getSearchField().addKeyListener(new KeyListener() {
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
        });
    }

    private LoanDetailController createControllerForLoan(Loan loan) {
        return new LoanDetailController("Ausleihe: " + loan.getCopy().getTitle().getName(), new LoanDetail(), loan, library);
    }


    private void filterTable() {
        TableModel tableModel = loanMaster.getLoanTable().getModel();
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        loanMaster.getLoanTable().setRowSorter(sorter);
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + loanMaster.getSearchField().getText()));
    }


    private void presentLoan(Loan loan) {
        if (masterDetailController != null) {
            ComponentController controller = loanControllerMap.get(loan);
            if (!masterDetailController.containsDetailController(controller)) {
                masterDetailController.addDetailController(controller);
            }
            masterDetailController.setSelectedDetailController(controller);
        }

    }

    private void removeControllerFromMap(ComponentController controller) {
        List<Loan> loansToRemove = new LinkedList<>();
        for (Loan loan : loanControllerMap.keySet()) {
            if (loanControllerMap.get(loan) == controller) {
                loansToRemove.add(loan);
            }
        }
        for (Loan loan : loansToRemove) {
            loanControllerMap.remove(loan);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    private void updateUI() {
        updateLabels();
        updateButtons();
        updateTable();
    }

    private void updateLabels() {
        loanMaster.getLoanCountLabel().setText(new Integer(library.getLoans().size()).toString());
        loanMaster.getCurrentLoanLabel().setText(new Integer(library.getLoans().size()).toString());
        loanMaster.getOverdueLoanLabel().setText(new Integer(library.getOverdueLoans().size()).toString());
    }

    private void updateButtons() {
        loanMaster.getShowSelctionButton().setEnabled(loanMaster.getLoanTable().getSelectedRowCount() > 0);
    }

    private void updateTable() {
        loanMaster.getLoanTable().updateUI();
    }

    @Override
    public void detailControllerDidCancel(BookDetailController bookDetailController) {
        removeControllerFromMap(bookDetailController);
        if (masterDetailController != null) masterDetailController.removeDetailController(bookDetailController);
    }

    @Override
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose) {
        if (shouldClose) {
            removeControllerFromMap(bookDetailController);
            if (masterDetailController != null) masterDetailController.removeDetailController(bookDetailController);
        }
    }

    @Override
    public void willRemoveDetailController(ComponentController detailController) {

    }

    @Override
    public void didRemoveDetailController(ComponentController detailController) {
        removeControllerFromMap(detailController);
    }

    @Override
    public void willSelectDetailController(ComponentController detailController) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void didSelectDetailController(ComponentController detailController) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void controllerDidChangeTitle(ComponentController controller) {

    }
}
