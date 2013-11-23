package ch.hsr.slibrary.gui.controller.customer;

import ch.hsr.slibrary.gui.controller.listener.EscapeKeyListener;
import ch.hsr.slibrary.gui.controller.loan.LoanListingController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.form.CustomerDetail;
import ch.hsr.slibrary.gui.form.LoanListing;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.gui.validation.EmptyTextValidation;
import ch.hsr.slibrary.gui.validation.IsIntRangeValidation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class CustomerDetailController extends ValidatableComponentController implements Observer {

    protected CustomerDetail customerDetail;
    protected LoanListingController loanListingController;
    protected Customer customer;
    protected Library library;
    private CustomerDetailControllerDelegate delegate;
    private MasterDetailController masterDetailController;

    public CustomerDetailController(String title, CustomerDetail component, Customer customer, Library library) {
        super(title);
        this.component = component;
        this.customerDetail = component;
        this.customer = customer;
        this.library = library;
        this.loanListingController = new LoanListingController("Ausleiheliste", new LoanListing(), customer, library);
        this.loanListingController.initialize();
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

    public CustomerDetailControllerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(CustomerDetailControllerDelegate delegate) {
        this.delegate = delegate;
    }

    private void initializeUI() {
        final CustomerDetailController self = this;

        customerDetail.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });

        customerDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValid()) {
                    saveChanges();
                    if (getDelegate() != null) getDelegate().detailControllerDidSave(self, true);
                }
            }
        });

        customerDetail.getContainer().addKeyListener(new EscapeKeyListener() {
            @Override
            public void escapeAction() {
                if (getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });
        customerDetail.getLoanListingPanel().add(loanListingController.getComponent().getContainer());
    }

    public void updateUI() {
        customerDetail.getFirstnameField().setText(customer.getName());
        customerDetail.getSurnameField().setText(customer.getSurname());
        customerDetail.getStreetField().setText(customer.getStreet());
        customerDetail.getCityField().setText(customer.getCity());
        customerDetail.getZipField().setText(Integer.valueOf(customer.getZip()).toString());
    }

    public void saveChanges() {
        customer.setName(customerDetail.getFirstnameField().getText());
        customer.setSurname(customerDetail.getSurnameField().getText());
        customer.setStreet(customerDetail.getStreetField().getText());
        customer.setCity(customerDetail.getCityField().getText());
        customer.setZip(Integer.valueOf(customerDetail.getZipField().getText()));
        setTitle(customer.getName() + " " + customer.getSurname());
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    @Override
    public void setValidations() {
        validationRules.add(new ValidationRule(new EmptyTextValidation(customerDetail.getFirstnameField(), "Firstname")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(customerDetail.getSurnameField(), "Surname")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(customerDetail.getStreetField(), "Street")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(customerDetail.getCityField(), "City")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(customerDetail.getZipField(), "ZIP")));
        validationRules.add(new ValidationRule(new IsIntRangeValidation(customerDetail.getZipField(), 0, 10000, "ZIP")));
    }
}
