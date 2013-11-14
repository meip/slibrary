package ch.hsr.slibrary.gui.controller.customer;

import ch.hsr.slibrary.gui.controller.listener.SearchableTableListener;
import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailControllerDelegate;
import ch.hsr.slibrary.gui.form.CustomerDetail;
import ch.hsr.slibrary.gui.form.CustomerMaster;
import ch.hsr.slibrary.spa.Customer;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class CustomerMasterController extends ComponentController implements Observer, CustomerDetailControllerDelegate, MasterDetailControllerDelegate {


    private MasterDetailController masterDetailController;
    private CustomerMaster customerMaster;
    private Library library;
    private Map<Customer, ComponentController> customerControllerMap = new HashMap<>();
    private ListSelectionModel listSelectionModel;

    public CustomerMasterController(String title, CustomerMaster component, Library lib) {
        super(title);
        this.component = component;
        customerMaster = component;
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
        initializeObserving();
        initializeSearchField();
        updateUI();
    }

    private void initializeButtonListeners() {

        final CustomerMasterController self = this;

        customerMaster.getEditCustomerButton().setEnabled(false);
        customerMaster.getEditCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : customerMaster.getCustomerTable().getSelectedRows()) {
                    index = customerMaster.getCustomerTable().convertRowIndexToModel(index);
                    Customer customer = library.getCustomers().get(index);
                    presentCustomer(customer);
                }
            }
        });

        customerMaster.getNewcustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewCustomerDetailController controller = new NewCustomerDetailController("Neuen Kunde erfassen", new CustomerDetail(), library);                        controller.setDelegate(self);
                controller.setDelegate(self);
                controller.setMasterDetailController(self.masterDetailController);
                masterDetailController.addDetailController(controller);
                masterDetailController.setSelectedDetailController(controller);
            }
        });

    }

    private void initializeTable() {
        customerMaster.getCustomerTable().setModel(new AbstractTableModel() {

            private String[] columnNames = {"Vorname", "Nachname", "Strasse", "Ort", "PLZ"};

            @Override
            public int getRowCount() {
                return library.getCustomers().size();
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
                Customer customer = library.getCustomers().get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return customer.getName();
                    case 1:
                        return customer.getSurname();
                    case 2:
                        return customer.getStreet();
                    case 3:
                        return customer.getCity();
                    case 4:
                        return customer.getZip();
                    default:
                        return "";
                }
            }
        });
        customerMaster.getCustomerTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable jtable = (JTable)e.getSource();
                    int index = jtable.convertRowIndexToModel(jtable.getSelectedRow());
                    Customer customer = library.getCustomers().get(index);
                    presentCustomer(customer);
                }
            }
        });

        listSelectionModel = customerMaster.getCustomerTable().getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateButtonState();
            }
        });
    }

    private void initializeObserving() {
        for (Customer customer : library.getCustomers()) {
            customer.addObserver(this);
        }
    }

    private void initializeSearchField() {
        customerMaster.getSearchField().addKeyListener(new SearchableTableListener(customerMaster.getCustomerTable(), customerMaster.getSearchField()));
    }

    private void removeControllerFromMap(ComponentController controller) {
        List<Customer> customersToRemove = new LinkedList<>();
        for (Customer customer : customerControllerMap.keySet()) {
            if (customerControllerMap.get(customer) == controller) {
                customersToRemove.add(customer);
            }
        }
        for (Customer customer : customersToRemove) {
            customerControllerMap.remove(customer);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    private void updateUI() {
        customerMaster.getCustomerTable().updateUI();
        updateButtonState();
    }

    private void updateButtonState() {
        customerMaster.getEditCustomerButton().setEnabled(customerMaster.getCustomerTable().getSelectedRowCount() > 0);
    }

    private void presentCustomer(Customer customer) {
        if (!customerControllerMap.containsKey(customer)) {
            CustomerDetailController controller = new CustomerDetailController(customer.getName() + " " + customer.getSurname(), new CustomerDetail(), customer, library);
            controller.setDelegate(this);
            controller.setMasterDetailController(this.masterDetailController);
            customerControllerMap.put(customer, controller);
        }
        ComponentController controller = customerControllerMap.get(customer);
        if (!masterDetailController.containsDetailController(controller)) {
            masterDetailController.addDetailController(controller);
        }
        masterDetailController.setSelectedDetailController(controller);
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

    @Override
    public void detailControllerDidCancel(CustomerDetailController customerDetailController) {
        removeControllerFromMap(customerDetailController);
        masterDetailController.removeDetailController(customerDetailController);
    }

    @Override
    public void detailControllerDidSave(CustomerDetailController customerDetailController, boolean shouldClose) {
        if (shouldClose) {
            removeControllerFromMap(customerDetailController);
            masterDetailController.removeDetailController(customerDetailController);
        }
    }
}
