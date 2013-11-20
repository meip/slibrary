package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.listener.EscapeKeyListener;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.gui.validation.EmptySelectionValidation;
import ch.hsr.slibrary.gui.validation.ErrorBorder;
import ch.hsr.slibrary.gui.validation.Validation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoanDetailController extends ValidatableComponentController implements Observer {

    protected LoanDetail loanDetail;
    protected Loan loan;
    protected Library library;
    private LoanDetailControllerDelegate delegate;

    private MasterDetailController masterDetailController;

    public LoanDetailController(String title, LoanDetail component, Loan loan, Library library) {
        super(title);
        this.component = component;
        this.loanDetail = component;
        this.loan = loan;
        this.library = library;
        this.setTitle(title);

        initialize();
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
        updateUI();
    }

    public LoanDetailControllerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(LoanDetailControllerDelegate delegate) {
        this.delegate = delegate;
    }

    private void initializeUI() {
        initializeCustomerSelectionUi();
        initializeCopySelectionUi();

        if (!loan.isLent()) {
            loanDetail.getCustomerSelect().setEnabled(false);
            loanDetail.getCopySelect().setEnabled(false);
            loanDetail.getReturnDateField().setEnabled(false);
        }

        final LoanDetailController self = this;
        loanDetail.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });

        loanDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValid()) {
                    saveChanges();
                    if (getDelegate() != null) getDelegate().detailControllerDidSave(self, true);
                }
            }
        });

        loanDetail.getContainer().addKeyListener(new EscapeKeyListener() {
            @Override
            public void escapeAction() {
                if (getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });
    }

    private void initializeCustomerSelectionUi() {

        loanDetail.getCustomerSelect().setModel(new ComboBoxModel<Customer>() {

            Customer selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                selectedItem = (Customer) anItem;
            }

            @Override
            public Object getSelectedItem() {
                return selectedItem;
            }

            @Override
            public int getSize() {
                return library.getCustomers().size();
            }

            @Override
            public Customer getElementAt(int index) {
                return library.getCustomers().get(index);
            }

            @Override
            public void addListDataListener(ListDataListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        loanDetail.getCustomerSelect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loanDetail.getCustomerSelect().getSelectedIndex() > 0) {
                    Customer customer = ((Customer) loanDetail.getCustomerSelect().getSelectedItem());
                    loanDetail.getLoansOverdueLabel().setText(String.valueOf(library.getOverdueLoansForCustomer(customer).size()));
                    loanDetail.getLoansCurrentLabel().setText(String.valueOf(library.getCustomerLoans(customer, true).size()));
                    if (library.getOverdueLoansForCustomer(customer).size() > 0) {
                        loanDetail.getLoansOverdueLabel().setForeground(Color.RED);
                        loanDetail.getSaveButton().setEnabled(false);
                    } else {
                        loanDetail.getLoansOverdueLabel().setForeground(Color.BLACK);
                        loanDetail.getSaveButton().setEnabled(true);
                    }
                }
            }
        });
        loanDetail.getCustomerSelect().setSelectedItem(loan.getCustomer());
        AutoCompleteDecorator.decorate(loanDetail.getCustomerSelect());
    }

    private void initializeCopySelectionUi() {
        loanDetail.getCopySelect().setModel(new ComboBoxModel<Long>() {
            Long selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                selectedItem = (Long) anItem;
            }

            @Override
            public Object getSelectedItem() {
                return selectedItem;
            }

            @Override
            public int getSize() {
                return library.getAvailableCopies().size();
            }

            @Override
            public Long getElementAt(int index) {
                return library.getAvailableCopies().get(index).getInventoryNumber();
            }

            @Override
            public void addListDataListener(ListDataListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        AutoCompleteDecorator.decorate(loanDetail.getCopySelect());
    }

    public void updateUI() {
        if (loan.getCustomer() != null) {
            loanDetail.getCustomerSelect().setSelectedIndex(library.getCustomers().indexOf(loan.getCustomer()));
        }
        if (loan.getCopy() != null) {
            // Make copy selection static and disable it
            loanDetail.getCopySelect().setModel(new ComboBoxModel<Long>(){
                @Override
                public void setSelectedItem(Object anItem) {

                }

                @Override
                public Object getSelectedItem() {
                    return loan.getCopy().getInventoryNumber();  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public int getSize() {
                    return 1;  //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public Long getElementAt(int index) {
                    return loan.getCopy().getInventoryNumber();
                }

                @Override
                public void addListDataListener(ListDataListener l) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void removeListDataListener(ListDataListener l) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            loanDetail.getCopySelect().setEnabled(false);
        }

        loanDetail.getReturnDateField().setText(LoanUtil.getReturnDate(loan, false));
    }

    public void saveChanges() {
        this.loan.setCustomer((Customer) loanDetail.getCustomerSelect().getSelectedItem());
        this.loan.setCopy(library.getCopyByInventoryNumber((long) loanDetail.getCopySelect().getSelectedItem()));
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    @Override
    public void setValidations() {
        validationRules.add(new ValidationRule(new EmptySelectionValidation(loanDetail.getCustomerSelect(), "Customer select") {
            @Override
            public boolean isValid() {
                return loanDetail.getCustomerSelect().getSelectedObjects().length > 0;
            }

        }));
        validationRules.add(new ValidationRule(new EmptySelectionValidation(loanDetail.getCopySelect(), "Inventory-ID") {
            @Override
            public boolean isValid() {
                return loanDetail.getCopySelect().getSelectedObjects().length > 0;
            }
        }));
    }
}
