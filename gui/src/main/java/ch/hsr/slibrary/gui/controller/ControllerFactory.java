package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.book.BookMasterController;
import ch.hsr.slibrary.gui.controller.customer.CustomerMasterController;
import ch.hsr.slibrary.gui.controller.loan.LoanMasterController;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.CustomerMaster;
import ch.hsr.slibrary.gui.form.LoanMaster;
import ch.hsr.slibrary.spa.Library;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 12.11.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
public class ControllerFactory {

    public static MDIntegratedTabbedController createBookSetup(Library library) {
        BookMasterController master = new BookMasterController("Bücher", new BookMaster(), library);
        master.initialize();
        MDIntegratedTabbedController masterDetail = new MDIntegratedTabbedController(master, "Bücher");
        master.setMasterDetailController(masterDetail);
        return masterDetail;
    }

    public static MDIntegratedTabbedController createLoanSetup(Library library) {
        LoanMasterController master = new LoanMasterController("Ausleihen", new LoanMaster(), library);
        master.initialize();
        MDIntegratedTabbedController masterDetail = new MDIntegratedTabbedController(master, "Ausleihen");
        master.setMasterDetailController(masterDetail);
        return masterDetail;
    }

    public static MDIntegratedTabbedController createCustomerSetup(Library library) {
        CustomerMasterController master = new CustomerMasterController("Kunden", new CustomerMaster(), library);
        master.initialize();
        MDIntegratedTabbedController masterDetail = new MDIntegratedTabbedController(master, "Kunden");
        master.setMasterDetailController(masterDetail);
        return masterDetail;
    }


}
