package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.gui.form.LoanListing;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.gui.validation.EmptySelectionValidation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class LoanDetailController extends ValidatableComponentController implements Observer {

    protected LoanDetail loanDetail;
    protected LoanListingController loanListingController;
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
        this.loanListingController = new LoanListingController("Ausleiheliste", new LoanListing(), loan.getCustomer(), loan, library);
        this.loanListingController.initialize();
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

        for (Loan loan : library.getCustomerLoans(this.loan.getCustomer())) {
            loan.addObserver(this);
        }
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

        TitledBorder border = (TitledBorder) loanDetail.getCopyPanel().getBorder();
        if (border != null) border.setTitle("Ausgeliehenes Exemplar");

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

        loanDetail.getLoanListingPanel().add(loanListingController.getComponent().getContainer());
        loanListingController.setSelectedLoan(loan);
        loanDetail.getReturnedCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        super.bindKeyStrokes();
        loanDetail.getLoanDetailPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "FINISHDETAIL");
        loanDetail.getLoanDetailPanel().getActionMap().put("FINISHDETAIL", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                loanDetail.getSaveButton().doClick();
                System.out.println("FINISHDETAIL action");
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
                    } else {
                        loanDetail.getLoansOverdueLabel().setForeground(Color.BLACK);
                    }
                    loanListingController.setCustomer(customer);
                    loanListingController.updateUI();
                }
                updateUI();
            }
        });
        loanDetail.getCustomerSelect().setSelectedItem(loan.getCustomer());
        AutoCompleteDecorator.decorate(loanDetail.getCustomerSelect());
    }

    private void initializeCopySelectionUi() {
        loanDetail.getCopySelect().setModel(new ComboBoxModel<Copy>() {
            Copy selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                if ((selectedItem != null && !selectedItem.equals(anItem)) ||
                        selectedItem == null && anItem != null) {
                    selectedItem = (Copy) anItem;
                }
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
            public Copy getElementAt(int index) {
                return library.getAvailableCopies().get(index);
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
        loanDetail.getCopySelect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUI();
            }
        });
        AutoCompleteDecorator.decorate(loanDetail.getCopySelect());
    }

    public void updateUI() {
        if (!isInSaveProgress) {
            loanDetail.getLentOnLabel().setText(LoanUtil.getPickupDate(loan));

            if (!loan.isLent()) {
                loanDetail.getCustomerSelect().setEnabled(false);
                loanDetail.getCopySelect().setEnabled(false);
            }


            loanDetail.getReturnedOnLabel().setText(loan.getReturnDate() != null ? LoanUtil.getReturnDate(loan, false) : "-");
            loanDetail.getReturnedCheckBox().setSelected(!loan.isLent());
            loanDetail.getReturnedCheckBox().setEnabled(loan.isLent());

            if (loan.getCustomer() != null) {
                loanDetail.getCustomerSelect().setSelectedIndex(library.getCustomers().indexOf(loan.getCustomer()));
            }

            if(loanDetail.getCopySelect().getSelectedIndex() > -1) {
                loanDetail.getBookLabel().setText(((Copy) loanDetail.getCopySelect().getSelectedItem()).getTitle().getName());
            }

            loanDetail.getLoanListingPanel().setVisible(loanDetail.getCustomerSelect().getSelectedIndex() > -1);

            if (loan.getCopy() != null) {

                // Make copy selection static and disable it
                loanDetail.getCopySelect().setModel(new ComboBoxModel<Copy>() {
                    @Override
                    public void setSelectedItem(Object anItem) {

                    }

                    @Override
                    public Object getSelectedItem() {
                        return loan.getCopy();
                    }

                    @Override
                    public int getSize() {
                        return 1;  //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public Copy getElementAt(int index) {
                        return loan.getCopy();
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

            loanDetail.getReturnDateLabel().setText(LoanUtil.getDueDateFormatted(loan));

            loanListingController.updateUI();
        }
    }

    public void saveChanges() {
        isInSaveProgress = true;
        loan.setCustomer((Customer) loanDetail.getCustomerSelect().getSelectedItem());
        loan.setCopy((Copy) loanDetail.getCopySelect().getSelectedItem());
        if (loanDetail.getReturnedCheckBox().isSelected()) {
            loan.returnCopy();
        }
        isInSaveProgress = false;
    }

    public LoanListingController getLoanListingController() {
        return loanListingController;
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

    @Override
     public void escapeComponent() {
        if (getDelegate() != null) getDelegate().detailControllerDidCancel(this);
    }

    @Override
     public void setFocus() {
        loanDetail.getCustomerSelect().requestFocusInWindow();
    }
}
