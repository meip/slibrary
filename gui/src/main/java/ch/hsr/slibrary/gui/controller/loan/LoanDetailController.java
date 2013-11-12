package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.controller.listener.EscapeKeyListener;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class LoanDetailController extends ValidatableComponentController implements Observer {

    private LoanDetail loanDetail;
    private Loan loan;
    private Library library;
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
        loanDetail.getCustomerSelect().setModel(new ComboBoxModel() {

            Object selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                selectedItem = anItem;
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
            public Object getElementAt(int index) {
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
        loanDetail.getCustomerSelect().setSelectedIndex(library.getCustomers().indexOf(loan.getCustomer()));

        if (!loan.isLent()) {
            loanDetail.getCustomerIdentityField().setEnabled(false);
            loanDetail.getCustomerSelect().setEnabled(false);
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
                saveChanges();
                if (isValid()) {
                    saveChanges();
                    if (getDelegate() != null) getDelegate().detailControllerDidSave(self, false);
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

    public void updateUI() {
        loanDetail.getCustomerIdentityField().setText(loan.getCustomer().getName());
    }

    public void saveChanges() {
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void setValidations() {
    }
}
