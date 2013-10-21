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

public class BookMasterController extends ComponentController implements Observer, WindowControllerDelegate, BookDetailControllerDelegate {


    private static final int DETAIL_MODE_TABBED = 0;
    private static final int DETAIL_MODE_STANDALONE = 1;

    private BookMaster bookMaster;
    private Library library;

    private int detailMode = DETAIL_MODE_TABBED;

    private TabController _tabController;
    private Map<Book, ComponentController> bookControllerMap = new HashMap<>();

    public BookMasterController(String title, BookMaster component, Library lib) {
        super(title);
        this.component = component;
        bookMaster = component;
        this.library = lib;
    }

    @Override
    public void initialize() {
        initializeButtonListeners();
        initializeBookList();
        initializeObserving();
        if(windowController != null) windowController.addDelegate(this);
        updateUI();
    }

    private void initializeButtonListeners() {
        final BookMasterController self = this;
        bookMaster.getDisplaySelectedButton().setEnabled(false);
        bookMaster.getDisplaySelectedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : bookMaster.getBooksList().getSelectedIndices()) {
                    Book book = library.getBooks().get(index);
                    if(!bookControllerMap.containsKey(book)) {
                        BookDetailController controller = createControllerForBook(book);
                        controller.setDelegate(self);
                        bookControllerMap.put(book, controller);
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
    private void initializeObserving() {
        for(Book book : library.getBooks()) {
            book.addObserver(this);
        }
    }

    private BookDetailController createControllerForBook(Book book) {
        String title = (detailMode == DETAIL_MODE_TABBED) ? BookUtil.shortBookName(book.getName()) : book.getName();
        return new BookDetailController(title, new BookDetail(), book, library);
    }

    private TabController getTabController() {
        if(_tabController == null) _tabController = new TabController("Detailansicht", new TabGUIComponent());
        return _tabController;
    }


    private void presentBook(Book book) {
        switch (detailMode) {
            case DETAIL_MODE_STANDALONE:
                presentBookInStandaloneFrame(book);
                break;

            case DETAIL_MODE_TABBED:
                presentBookInTabs(book);
                break;
        }
    }


    private void presentBookInStandaloneFrame(Book book) {
        if(!windowController.containsController(bookControllerMap.get(book))) {
            windowController.presentControllerAsFrame(bookControllerMap.get(book));
            windowController.arrangeControllerWithPosition(bookControllerMap.get(book), WindowBounds.WINDOW_POSITION_RIGHT_TOP);
        } else {
            windowController.bringToFront(bookControllerMap.get(book));
        }
    }

    private void presentBookInTabs(Book book) {
        if(!getTabController().containsController(bookControllerMap.get(book))) {
            getTabController().addController(bookControllerMap.get(book));
        }
        getTabController().showController(bookControllerMap.get(book));

        if(!windowController.containsController(getTabController())) {
            windowController.presentControllerAsFrame(getTabController());
            windowController.arrangeControllerWithPosition(getTabController(), WindowBounds.WINDOW_POSITION_FILL_RIGHT);
        } else {
            windowController.bringToFront(getTabController());
        }


    }

    private  void dismissBook(Book book) {
        dismissBookController(bookControllerMap.get(book));
    }

    private void dismissBookController(ComponentController controller) {
        switch (detailMode) {
            case DETAIL_MODE_STANDALONE:
                windowController.dismissController(controller);
                break;

            case DETAIL_MODE_TABBED:
                if(controller == getTabController()) {
                    getTabController().removeAllControllers();
                    bookControllerMap = new HashMap<>();
                } else {
                    getTabController().removeController(controller);
                    if(getTabController().getControllers().size() == 0) {
                        windowController.dismissController(getTabController());
                    }
                }
                break;
        }
        removeControllerFromMap(controller);

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
        bookMaster.getBooksAmountLabel().setText(new Integer(library.getBooks().size()).toString());
        bookMaster.getCopyAmountLabel().setText(new Integer(library.getCopies().size()).toString());
        bookMaster.getDisplaySelectedButton().setEnabled(bookMaster.getBooksList().getSelectedIndices().length > 0);
        bookMaster.getBooksList().updateUI();
    }


    @Override
    public void windowDidOpenController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowWillCloseController(WindowController windowController, ComponentController controller) {
        dismissBookController(controller);
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

    @Override
    public void detailControllerDidCancel(BookDetailController bookDetailController) {
        dismissBookController(bookDetailController);
    }

    @Override
    public void detailControllerDidSave(BookDetailController bookDetailController) {
        dismissBookController(bookDetailController);
    }
}
