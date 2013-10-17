package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.GUIComponent;
import ch.hsr.slibrary.gui.form.TabGUIComponent;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BookMasterController extends ComponentController implements Observer {


    private BookMaster bookMaster;
    private Library library;


    public BookMasterController(String title, BookMaster component, Library lib) {
        super(title);
        this.component = component;
        bookMaster = component;
        this.library = lib;

        initializeButtonListeners();
        initializeBookList();
        updateUI();
    }

    private void initializeButtonListeners() {
        bookMaster.getDisplaySelectedButton().setEnabled(false);
        bookMaster.getDisplaySelectedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(bookMaster.getBooksList().getSelectedIndices().length == 1) {
                    presentBookDetailInFrame(library.getBooks().get(bookMaster.getBooksList().getSelectedIndex()));
                } else if(bookMaster.getBooksList().getSelectedIndices().length > 1) {
                    List<Book> books = new ArrayList<>();
                    for (int index : bookMaster.getBooksList().getSelectedIndices()) {
                        books.add(library.getBooks().get(index));
                    }
                    presentMultipleBookDetailsInTabs(books);
                }
            }
        });

        bookMaster.getAddBookButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowController.presentControllerAsFrame(
                        new NewBookDetailController(
                                "Neues Buch erfassen",
                                new BookDetail(),
                                library
                        )
                );
            }
        });

    }
    private void initializeBookList() {
        bookMaster.getBooksList().setModel(new ListModel() {
            @Override
            public int getSize() {
                return library.getBooks().size();
            }

            @Override
            public Object getElementAt(int index) {
                return library.getBooks().get(index).toString();
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

        bookMaster.getBooksList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateUI();
            }
        });

    }

    private void updateUI() {
        bookMaster.getBooksAmountLabel().setText(new Integer(library.getBooks().size()).toString());
        bookMaster.getCopyAmountLabel().setText(new Integer(library.getCopies().size()).toString());
        bookMaster.getDisplaySelectedButton().setEnabled(bookMaster.getBooksList().getSelectedIndices().length > 0);
        bookMaster.getBooksList().updateUI();
    }

    private void presentBookDetailInFrame(Book book) {
        windowController.presentControllerAsFrame(new BookDetailController(book.getName(), new BookDetail(),book,library));
    }

    private  void presentMultipleBookDetailsInTabs(List<Book> books) {
        TabController tabController = new TabController(new TabGUIComponent());
        List<ComponentController> controllers = new ArrayList<>();
        for(Book book : books) {
            controllers.add(new BookDetailController(book.getName(), new BookDetail(),book,library));
        }
        tabController.setControllers(controllers);
        windowController.presentControllerAsFrame(tabController);
    }


    @Override
    public void update(Observable o, Object arg) {



    }
}
