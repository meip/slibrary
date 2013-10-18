package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.TabGUIComponent;
import ch.hsr.slibrary.gui.util.BookUtil;
import ch.hsr.slibrary.gui.util.WindowBounds;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BookMasterController extends ComponentController implements Observer, WindowControllerDelegate {


    private static final int DETAIL_MODE_TABBED = 0;
    private static final int DETAIL_MODE_STANDALONE = 1;

    private BookMaster bookMaster;
    private Library library;

    private int detailMode = DETAIL_MODE_STANDALONE;

    private ComponentController bookDetailController;
    private Map<Book, ComponentController> bookControllerMap = new HashMap<>();

    public BookMasterController(String title, BookMaster component, Library lib) {
        super(title);
        this.component = component;
        bookMaster = component;
        this.library = lib;
    }

    public void initialize() {
        initializeButtonListeners();
        initializeBookList();
        if(windowController != null) windowController.addDelegate(this);
        updateUI();
    }

    private void initializeButtonListeners() {
        bookMaster.getDisplaySelectedButton().setEnabled(false);
        bookMaster.getDisplaySelectedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : bookMaster.getBooksList().getSelectedIndices()) {
                    Book book = library.getBooks().get(index);
                    if(!bookControllerMap.containsKey(book)) {
                        bookControllerMap.put(book, createControllerForBook(book));
                    }
                    presentBook(book);
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

    private ComponentController createControllerForBook(Book book) {
        return new BookDetailController(book.getName(), new BookDetail(), book, library);
    }


    private void presentBook(Book book) {
        if(detailMode == DETAIL_MODE_STANDALONE) {
            presentBookInStandaloneFrame(book);
        }
    }


    private void presentBookInStandaloneFrame(Book book) {
        if(!windowController.containsController(bookControllerMap.get(book))) {
            windowController.presentControllerAsFrame(bookControllerMap.get(book));
        } else {
            windowController.bringToFront(bookControllerMap.get(book));
        }
        windowController.arrangeControllerWithPosition(bookControllerMap.get(book), WindowBounds.WINDOW_POSITION_RIGHT_TOP);
    }

    private  void presentMultipleBookDetailsInTabs(List<Book> books) {
        TabController tabController = new TabController(new TabGUIComponent());
        List<ComponentController> controllers = new ArrayList<>();
        for(Book book : books) {
            controllers.add(new BookDetailController(BookUtil.shortBookName(book.getName()), new BookDetail(), book, library));
        }
        tabController.setControllers(controllers);
        windowController.presentControllerAsFrame(tabController);
        bookDetailController = tabController;
    }


    @Override
    public void update(Observable o, Object arg) {



    }


    @Override
    public void windowDidOpenController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowWillCloseController(WindowController windowController, ComponentController controller) {
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
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDidActivateController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDidDeactivateController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
