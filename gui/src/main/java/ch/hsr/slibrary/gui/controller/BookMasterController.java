package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailControllerDelegate;
import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BookMasterController extends ComponentController implements Observer, BookDetailControllerDelegate, MasterDetailControllerDelegate {



    private MasterDetailController masterDetailController;
    private BookMaster bookMaster;
    private Library library;
    private Map<Book, ComponentController> bookControllerMap = new HashMap<>();
    private ListSelectionModel listSelectionModel;

    public BookMasterController(String title, BookMaster component, Library lib) {
        super(title);
        this.component = component;
        bookMaster = component;
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
        updateUI();
    }

    private void initializeButtonListeners() {

        final BookMasterController self = this;

        bookMaster.getDisplaySelectedButton().setEnabled(false);
        bookMaster.getDisplaySelectedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : bookMaster.getTable().getSelectedRows()) {
                    Book book = library.getBooks().get(index);
                    if(!bookControllerMap.containsKey(book)) {
                        BookDetailController controller = createControllerForBook(book);
                        controller.setDelegate(self);
                        controller.setMasterDetailController(self.masterDetailController);
                        bookControllerMap.put(book, controller);
                    }
                    presentBook(book);
                }
            }
        });

        bookMaster.getAddBookButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(masterDetailController != null) {
                    ComponentController controller = new NewBookDetailController("Neues Buch erfassen",new BookDetail(),library);
                    masterDetailController.addDetailController(controller);
                    masterDetailController.setSelectedDetailController(controller);
                }
            }
        });



    }
    private void initializeTable() {
        bookMaster.getTable().setModel(new AbstractTableModel() {

            private String[] columnNames = {"Titel", "Autor", "Publisher"};
            @Override
            public int getRowCount() {
                return library.getBooks().size();
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
                Book book = library.getBooks().get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return book.getName();
                    case 1:
                        return book.getAuthor();
                    case 2:
                        return book.getPublisher();
                    default:
                        return "";
                }
            }
        });
        listSelectionModel = bookMaster.getTable().getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateLabels();
                updateButtons();
            }
        });
    }

    private void initializeObserving() {
        for(Book book : library.getBooks()) {
            book.addObserver(this);
        }
    }

    private BookDetailController createControllerForBook(Book book) {
        return new BookDetailController(book.getName(), new BookDetail(), book, library);
    }



    private void presentBook(Book book) {
        if(masterDetailController != null) {
            ComponentController controller = bookControllerMap.get(book);
            if(!masterDetailController.containsDetailController(controller)) {
                masterDetailController.addDetailController(controller);
            }
            masterDetailController.setSelectedDetailController(controller);
        }

    }

    private void removeControllerFromMap(ComponentController controller) {
        List<Book> booksToRemove = new LinkedList<>();
        for (Book book : bookControllerMap.keySet()) {
            if(bookControllerMap.get(book) == controller) {
                booksToRemove.add(book);
            }
        }
        for(Book book : booksToRemove) {
            bookControllerMap.remove(book);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    private void updateUI() {
        updateLabels();
        updateButtons();
        updateTable();
    }

    private void updateLabels() {
        bookMaster.getBooksAmountLabel().setText(new Integer(library.getBooks().size()).toString());
        bookMaster.getCopyAmountLabel().setText(new Integer(library.getCopies().size()).toString());
        bookMaster.getNumSelectedLabel().setText(new Integer(bookMaster.getTable().getSelectedRowCount()).toString());
    }

    private void updateButtons() {
        bookMaster.getDisplaySelectedButton().setEnabled(bookMaster.getTable().getSelectedRowCount() > 0);
    }

    private void updateTable() {
        bookMaster.getTable().updateUI();
    }

    @Override
    public void detailControllerDidCancel(BookDetailController bookDetailController) {
        removeControllerFromMap(bookDetailController);
        if(masterDetailController != null) masterDetailController.removeDetailController(bookDetailController);
    }

    @Override
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose) {
        if(shouldClose) {
            removeControllerFromMap(bookDetailController);
            if(masterDetailController != null) masterDetailController.removeDetailController(bookDetailController);
        }
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
}
