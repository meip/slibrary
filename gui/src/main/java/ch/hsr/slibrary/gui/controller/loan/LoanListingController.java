package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.listener.EscapeKeyListener;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.form.CustomerDetail;
import ch.hsr.slibrary.gui.form.LoanListing;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.gui.util.TableHelper;
import ch.hsr.slibrary.gui.validation.EmptyTextValidation;
import ch.hsr.slibrary.gui.validation.IsIntRangeValidation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class LoanListingController extends ValidatableComponentController implements Observer {

    protected LoanListing loanListing;
    protected Customer customer;
    protected Library library;
    private LoanListingControllerDelegate delegate;
    private MasterDetailController masterDetailController;
    private Loan loan;


    public LoanListingController(String title, LoanListing component, Customer customer, Library library) {
        super(title);
        this.component = component;
        this.loanListing = component;
        this.customer = customer;
        this.library = library;
        initialize();
    }

    public LoanListingController(String title, LoanListing component, Customer customer, Loan loan, Library library) {
        this(title,component,customer,library);
        this.loan = loan;
    }

    public MasterDetailController getMasterDetailController() {
        return masterDetailController;
    }

    public void setMasterDetailController(MasterDetailController masterDetailController) {
        this.masterDetailController = masterDetailController;
    }

    @Override
    public void initialize() {
        initializeUI();
        setValidations();
    }

    private void initializeUI() {
        initializeTable();
        final LoanListingController self = this;
        loanListing.getReturnSelectedCopyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Loan> loans = new LinkedList<>();
                for (int index : loanListing.getCopyTable().getSelectedRows()) {
                    index = loanListing.getCopyTable().convertRowIndexToModel(index);
                    /*library.getCustomerLoans(customer).get(index).returnCopy();
                    loanListing.getCopyTable().updateUI();
                    customer.notifyObservers();*/
                    loans.add(library.getCustomerLoans(customer).get(index));
                }
                if(self.delegate != null) {
                    self.delegate.loanListingControllerDidSelectLoans(self, loans);
                }
            }
        });
    }

    private void initializeTable() {
        TableHelper.setAlternatingRowStyle(loanListing.getCopyTable());
        loanListing.getCopyTable().setPreferredScrollableViewportSize(loanListing.getCopyTable().getPreferredSize());


        loanListing.getCopyTable().setModel(new AbstractTableModel() {
            private final int ICON_COLUMN = 0;
            private String[] columnNames = {"Status", "Title", "Exemplar-ID", "Ausgeliehen am", "Ausgeliehen bis"};

            @Override
            public int getRowCount() {
                return library.getCustomerLoans(customer).size();
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
                Loan loan = library.getCustomerLoans(customer).get(rowIndex);
                switch (columnIndex) {
                    case ICON_COLUMN:
                        return new ImageIcon(getClass().getClassLoader().getResource((loan.isLent()) ? "error_10x10.png" : "correct_10x10.png" ));
                    case 1:
                        return loan.getCopy().getTitle().getName();
                    case 2:
                        return loan.getCopy().getInventoryNumber();
                    case 3:
                        return LoanUtil.getPickupDate(loan);
                    case 4:
                        return LoanUtil.getReturnDate(loan);
                    default:
                        return "";
                }
            }
            public Class getColumnClass(int column) {
                Class clazz = String.class;
                switch (column) {
                    case ICON_COLUMN:
                        clazz = Icon.class;
                        break;
                }
                return clazz;
            }
        });

        if(loan != null) {
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(loanListing.getCopyTable().getModel());
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                public boolean include(Entry entry) {
                    int i = (int) entry.getIdentifier();
                    return library.getCustomerLoans(customer).get(i) != loan;
                }
            };
            sorter.setRowFilter(filter);
            loanListing.getCopyTable().setRowSorter(sorter);
        }
    }

    public void updateUI() {
        initializeTable();
    }

    public void saveChanges() {
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    @Override
    public void setValidations() {
    }

    public void setSelectedLoan(Loan selectedLoan) {
        List<Loan> loanList = library.getCustomerLoans(customer);
        int i=0;
        for (Iterator<Loan> iterator = loanList.iterator(); iterator.hasNext(); i++ ) {
            Loan next = iterator.next();
            if(selectedLoan.equals(next)) loanListing.getCopyTable().changeSelection(loanListing.getCopyTable().convertRowIndexToView(i), 0, false, false);
        }
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LoanListingControllerDelegate getListingDelegate() {
        return delegate;
    }

    public void setListingDelegate(LoanListingControllerDelegate delegate) {
        this.delegate = delegate;
    }
}
