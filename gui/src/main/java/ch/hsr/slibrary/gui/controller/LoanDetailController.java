package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
        final LoanDetailController self = this;
        loanDetail.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });

        loanDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                if (isValid()) {
                    saveChanges();
                    if(getDelegate() != null) getDelegate().detailControllerDidSave(self, false);
                }
            }
        });

        loanDetail.getContainer().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if(getDelegate() != null) getDelegate().detailControllerDidCancel(self);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
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
