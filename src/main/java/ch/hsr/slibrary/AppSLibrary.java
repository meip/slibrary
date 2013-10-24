package ch.hsr.slibrary;

import ch.hsr.slibrary.gui.controller.*;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.spa.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

/**
 * User: p1meier
 * Date: 15.10.13
 */
public class AppSLibrary implements MainMenuBarControllerDelegate{


    private WindowController windowController;
    private MasterDetailController masterDetailController;
    private MainMenuBarController menuBarController;
    private BookMasterController bookMasterController;
    private Library library;


    public AppSLibrary(Library library) {
        this.library = library;

        String os = System.getProperty("os.name").toLowerCase();
        boolean isMac = os.startsWith("mac");
        if(isMac) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Swinging Library");
        }

        initializeControllerMainControllers();

    }

    private void initializeControllerMainControllers() {
        windowController = new WindowController();
        menuBarController = new MainMenuBarController(new JMenuBar(), windowController);
        windowController.setMenuBarForAllControllers(menuBarController);
        windowController.addDelegate(menuBarController);
        menuBarController.setDelegate(this);
        bookMasterController = new BookMasterController("Bücher", new BookMaster(), library);
        bookMasterController.initialize();


        setTwoWindowsMode();

    }


    private void setOneWindowMode() {
        menuBarController.setSelectedViewItemName(MainMenuBarController.MENU_VIEW_ONE_WINDOW);
        List<ComponentController> detailControllers = new LinkedList<>();


        if(masterDetailController != null) {
            detailControllers = masterDetailController.getDetailControllers();
            masterDetailController.dismiss();
        }
        masterDetailController = new SingleFrameTabMDController(windowController, bookMasterController, "Detailansicht");
        masterDetailController.setDetailControllers(detailControllers);
        bookMasterController.setMasterDetailController(masterDetailController);

    }

    private void setTwoWindowsMode() {
        menuBarController.setSelectedViewItemName(MainMenuBarController.MENU_VIEW_TWO_WINDOWS);
        List<ComponentController> detailControllers = new LinkedList<>();


        if(masterDetailController != null) {
            detailControllers = masterDetailController.getDetailControllers();
            masterDetailController.dismiss();
        }
        masterDetailController = new DoubleFrameTabMDController(windowController, bookMasterController, "Detailansicht");
        masterDetailController.setDetailControllers(detailControllers);
        bookMasterController.setMasterDetailController(masterDetailController);
    }


    public static void main(String[] args) throws Exception {
        Library library = new Library();
        initLibrary(library);
        new AppSLibrary(library);

    }

    private static void initLibrary(Library library)
            throws ParserConfigurationException, SAXException, IOException,
            IllegalLoanOperationException {

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        loadCustomersFromXml(library, builder, new File("data/customers.xml"));

        loadBooksFromXml(library, builder, new File("data/books.xml"));

        // create pseudo random books and loans
        createBooksAndLoans(library);

        System.out.println("Initialisation of the library was successful!\n");
        System.out.println("Books in library: " + library.getBooks().size());
        System.out.println("Customers: " + library.getCustomers().size() + "\n");
        System.out.println("Copies in library: " + library.getCopies().size());
        System.out.println("Copies currently on loan: " + library.getLentOutBooks().size());
        int lentBooksPercentage = (int) (((double) library.getLentOutBooks().size()) / library.getCopies().size() * 100);
        System.out.println("Percent copies on loan: " + lentBooksPercentage + "%");
        System.out.println("Copies currently overdue: " + library.getOverdueLoans().size());

        for (Loan l : library.getOverdueLoans())
            System.out.println(l.getDaysOverdue());
    }

    private static void createBooksAndLoans(Library library)
            throws IllegalLoanOperationException {
        for (int i = 0; i < library.getBooks().size(); i++) {
            switch (i % 4) {
                case 0:
                    Copy c1 = library.createAndAddCopy(library.getBooks().get(i));
                    c1.setCondition(Copy.Condition.GOOD);
                    createLoansForCopy(library, c1, i, 5);
                    Copy c2 = library.createAndAddCopy(library.getBooks().get(i));
                    c2.setCondition(Copy.Condition.DAMAGED);
                    createLoansForCopy(library, c2, i, 2);
                    Copy c3 = library.createAndAddCopy(library.getBooks().get(i));
                    c3.setCondition(Copy.Condition.WASTE);
                    break;
                case 1:
                    Copy c4 = library.createAndAddCopy(library.getBooks().get(i));
                    createLoansForCopy(library, c4, i, 4);
                    library.createAndAddCopy(library.getBooks().get(i));
                    break;
                case 2:
                    Copy c5 = library.createAndAddCopy(library.getBooks().get(i));
                    createLoansForCopy(library, c5, i, 2);
                    break;
                case 3:
                    Copy c6 = library.createAndAddCopy(library.getBooks().get(i));
                    createOverdueLoanForCopy(library, c6, i);
                    break;
            }
        }
    }

    private static void loadBooksFromXml(Library library,
                                         DocumentBuilder builder, File file) throws SAXException, IOException {
        Document doc2 = builder.parse(file);
        NodeList titles = doc2.getElementsByTagName("title");
        for (int i = 0; i < titles.getLength(); i++) {
            Node title = titles.item(i);
            Book b = library.createAndAddBook(getTextContentOf(title, "name"));
            b.setAuthor(getTextContentOf(title, "author"));
            b.setPublisher(getTextContentOf(title, "publisher"));
            b.setShelf(Shelf.A1);
        }
    }

    private static void loadCustomersFromXml(Library library, DocumentBuilder builder, File file) throws SAXException, IOException {
        Document doc = builder.parse(file);
        NodeList customers = doc.getElementsByTagName("customer");
        for (int i = 0; i < customers.getLength(); i++) {
            Node customer = customers.item(i);

            Customer c = library.createAndAddCustomer(getTextContentOf(customer, "name"), getTextContentOf(customer, "surname"));
            c.setAdress(getTextContentOf(customer, "street"), Integer.parseInt(getTextContentOf(customer, "zip")), getTextContentOf(customer, "city"));
        }
    }

    private static void createLoansForCopy(Library library, Copy copy, int position,
                                           int count) throws IllegalLoanOperationException {
        // Create Loans in the past
        for (int i = count; i > 1; i--) {
            Loan l = library.createAndAddLoan(getCustomer(library, position + i), copy);
            GregorianCalendar pickup = l.getPickupDate();
            pickup.add(GregorianCalendar.MONTH, -i);
            pickup.add(GregorianCalendar.DAY_OF_MONTH, position % 10);
            l.setPickupDate(pickup);
            GregorianCalendar ret = (GregorianCalendar) pickup.clone();
            ret.add(GregorianCalendar.DAY_OF_YEAR, position % 10 + i * 2);
            l.returnCopy(ret);
        }
        // Create actual open loans
        if (position % 2 == 0) {
            Loan l = library.createAndAddLoan(getCustomer(library, position), copy);
            GregorianCalendar pickup = l.getPickupDate();
            pickup.add(GregorianCalendar.DAY_OF_MONTH, -position % 10);
            l.setPickupDate(pickup);
        }
    }

    private static void createOverdueLoanForCopy(Library library, Copy copy, int position)
            throws IllegalLoanOperationException {
        Loan l = library.createAndAddLoan(getCustomer(library, position), copy);
        GregorianCalendar pickup = l.getPickupDate();
        pickup.add(GregorianCalendar.MONTH, -1);
        pickup.add(GregorianCalendar.DAY_OF_MONTH, -position % 15);
        l.setPickupDate(pickup);
    }

    private static Customer getCustomer(Library library, int position) {
        return library.getCustomers().get(position % library.getCustomers().size());
    }

    private static String getTextContentOf(Node element, String name) {
        NodeList attributes = element.getChildNodes();
        for (int r = 0; r < attributes.getLength(); r++) {
            if (attributes.item(r).getNodeName().equals(name)) {
                return attributes.item(r).getTextContent();
            }
        }
        return "";
    }

    @Override
    public void didChangeDisplayMode(MainMenuBarController controller) {
        switch (controller.getSelectedViewItemName()) {
            case MainMenuBarController.MENU_VIEW_ONE_WINDOW:
                setOneWindowMode();
                break;

            case MainMenuBarController.MENU_VIEW_TWO_WINDOWS:
                setTwoWindowsMode();
                break;
        }
    }
}
