package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.controller.listener.SearchableTableListener;
import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailControllerDelegate;
import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.util.LoanUtil;
import ch.hsr.slibrary.gui.util.TableHelper;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
        initializeSearchField();
        updateUI();
    }

    private void initializeButtonListeners() {
        final BookMasterController self = this;
        bookMaster.getDisplaySelectedButton().setEnabled(false);
        bookMaster.getDisplaySelectedButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (int index : bookMaster.getTable().getSelectedRows()) {
                    index = bookMaster.getTable().convertRowIndexToModel(index);
                    Book book = library.getBooks().get(index);
                    presentBook(book);
                }
            }
        });

        bookMaster.getAddBookButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewBookDetailController controller = new NewBookDetailController("Neues Buch erfassen", new BookDetail(), library);
                controller.setDelegate(self);
                controller.setMasterDetailController(self.masterDetailController);
                masterDetailController.addDetailController(controller);
                masterDetailController.setSelectedDetailController(controller);
            }
        });
        bookMaster.getAddBookButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                "NEWBOOK");
        bookMaster.getAddBookButton().getActionMap().put("NEWBOOK", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                bookMaster.getAddBookButton().doClick();
                System.out.println("NEWBOOK action");
            }
        });
        bookMaster.getTable().getInputMap(JComponent.WHEN_FOCUSED).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                "SHOWSELECTEDBOOK");
        bookMaster.getTable().getActionMap().put("SHOWSELECTEDBOOK", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                bookMaster.getDisplaySelectedButton().doClick();
                System.out.println("SHOWSELECTEDBOOK action");
            }
        });

    }

    private void initializeTable() {
        TableHelper.setAlternatingRowStyle(bookMaster.getTable());
        bookMaster.getTable().setModel(new AbstractTableModel() {

            private String[] columnNames = {"Verfügbar", "Titel", "Autor", "Publisher", "Regal"};

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
                        List<Copy> availableCopies = library.getAvailableCopies();
                        for (Iterator<Copy> it = availableCopies.iterator(); it.hasNext(); ) {
                            if (!it.next().getTitle().equals(book))
                                it.remove();
                        }
                        if (availableCopies.size() == 0 && library.getLentCopiesOfBook(book).size() > 0) {
                            List<Loan> loans = library.getLentCopiesOfBook(book);
                            GregorianCalendar lentUntil = loans.get(0).getDueDate();
                            for (Loan loan : loans) {
                                lentUntil = (loan.getDueDate().getTimeInMillis() < lentUntil.getTimeInMillis()) ? loan.getDueDate() : lentUntil;
                            }
                            if (Calendar.getInstance().getTimeInMillis() > lentUntil.getTimeInMillis()) {
                                return LoanUtil.LOAN_IS_OVERDUE;
                            } else {
                                long diffTime = lentUntil.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                                long daysOverDue = diffTime / (1000 * 60 * 60 * 24);
                                return "ab " + new SimpleDateFormat("dd.MM.yyyy").format(new Long(lentUntil.getTimeInMillis())) + " (" + daysOverDue + " Tage)";
                            }
                        } else {
                            return availableCopies.size();
                        }
                    case 1:
                        return book.getName();
                    case 2:
                        return book.getAuthor();
                    case 3:
                        return book.getPublisher();
                    case 4:
                        return book.getShelf();
                    default:
                        return "";
                }
            }
        });
        bookMaster.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable jtable = (JTable) e.getSource();
                    int index = jtable.convertRowIndexToModel(jtable.getSelectedRow());
                    Book book = library.getBooks().get(index);
                    presentBook(book);
                }
            }
        });

       /* bookMaster.getTable().setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table,
                        value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.white : Color.LIGHT_GRAY);
                return c;
            }
        });*/

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
        library.addObserver(this);
        for (Book book : library.getBooks()) {
           book.addObserver(this);
        }
    }

    private void initializeSearchField() {
        bookMaster.getSearchField().addKeyListener(new SearchableTableListener(bookMaster.getTable(), bookMaster.getSearchField()));
        bookMaster.getSearchField().requestFocusInWindow();
        bookMaster.getSearchField().requestFocus();
    }

    private BookDetailController createControllerForBook(Book book) {
        return new BookDetailController(book.getName(), new BookDetail(), book, library);
    }

    private void presentBook(Book book) {
        if (!bookControllerMap.containsKey(book)) {
            BookDetailController controller = createControllerForBook(book);
            controller.setDelegate(this);
            controller.setMasterDetailController(this.masterDetailController);
            bookControllerMap.put(book, controller);
        }
        ComponentController controller = bookControllerMap.get(book);
        if (!masterDetailController.containsDetailController(controller)) {
            masterDetailController.addDetailController(controller);
        }
        masterDetailController.setSelectedDetailController(controller);
    }

    private void removeControllerFromMap(ComponentController controller) {
        List<Book> booksToRemove = new LinkedList<>();
        for (Book book : bookControllerMap.keySet()) {
            if (bookControllerMap.get(book) == controller) {
                booksToRemove.add(book);
            }
        }
        for (Book book : booksToRemove) {
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
        ((AbstractTableModel)bookMaster.getTable().getModel()).fireTableDataChanged();
    }

    @Override
    public void detailControllerDidCancel(BookDetailController bookDetailController) {
        removeControllerFromMap(bookDetailController);
        masterDetailController.removeDetailController(bookDetailController);
        updateUI();
    }

    @Override
    public void detailControllerDidSave(BookDetailController bookDetailController, boolean shouldClose) {
        if (shouldClose) {
            removeControllerFromMap(bookDetailController);
            masterDetailController.removeDetailController(bookDetailController);
        }
        updateUI();
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
    }

    @Override
    public void didSelectDetailController(ComponentController detailController) {
        detailController.setFocus();
    }

    @Override
    public void controllerDidChangeTitle(ComponentController controller) {

    }
}
