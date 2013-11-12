package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.CustomerDetail;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCustomerDetailController extends CustomerDetailController {

    public NewCustomerDetailController(String title, CustomerDetail component, Library library) {
        super(title, component, new Customer("", ""), library);
        initializeSaveButton();
    }

    public void initializeSaveButton() {
        final CustomerDetailController self = this;
        customerDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValid()) {
                    saveChanges();
                    library.getCustomers().add(customer);
                    if (getDelegate() != null) getDelegate().detailControllerDidSave(self, true);
                }
            }
        });
    }

}
