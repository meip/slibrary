package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.util.TableHelper;
import ch.hsr.slibrary.gui.validation.EmptyTextValidation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Shelf;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class BookDetailController extends ValidatableComponentController implements Observer {

    protected BookDetail bookDetail;
    protected Book book;
    protected Library library;
    protected BookDetailControllerDelegate delegate;

    private MasterDetailController masterDetailController;

    public BookDetailController(String title, BookDetail component, Book book, Library library) {
        super(title);
        this.component = component;
        this.bookDetail = component;
        this.book = book;
        this.library = library;
        this.setTitle(title);

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
        book.addObserver(this);
        initializeUI();
        setValidations();
        updateUI();
    }

    public BookDetailControllerDelegate getTabDelegate() {
        return delegate;
    }

    public void setDelegate(BookDetailControllerDelegate delegate) {
        this.delegate = delegate;
    }

    private void initializeUI() {

        bookDetail.getAddCopyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCopyPressed();
            }
        });

        bookDetail.getShelfComboBox().setModel(new ComboBoxModel() {

            Object selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                selectedItem = anItem;
            }

            @Override
            public Object getSelectedItem() {
                return selectedItem;
            }

            @Override
            public int getSize() {
                return Shelf.values().length;
            }

            @Override
            public Object getElementAt(int index) {
                return Shelf.values()[index];
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

        final BookDetailController self = this;
        bookDetail.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelPressed();
            }
        });

        bookDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePressed();
            }
        });

        final JComboBox cb = new JComboBox();
        cb.setModel(new ComboBoxModel<Copy.Condition>() {
            Copy.Condition selectedItem;

            @Override
            public void setSelectedItem(Object anItem) {
                selectedItem = (Copy.Condition) anItem;
            }

            @Override
            public Object getSelectedItem() {
                return selectedItem;
            }

            @Override
            public int getSize() {
                return Copy.Condition.values().length;
            }

            @Override
            public Copy.Condition getElementAt(int index) {
                return Copy.Condition.values()[index];
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


        TableHelper.setAlternatingRowStyle(bookDetail.getCopyTable());
        bookDetail.getCopyTable().setModel(new TableModel() {
            private String[] columnNames = {"ID", "Zustand"};

            @Override
            public int getRowCount() {
                return library.getCopiesOfBook(book).size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1:
                        return JComboBox.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return (columnIndex == 1) ? true : false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Copy copy = library.getCopiesOfBook(book).get(rowIndex);
                switch (columnIndex) {
                    case 0:
                        return "#" + copy.getInventoryNumber();
                    case 1:
                        return Copy.Condition.valueOf(copy.getCondition().name());
                    default:
                        return null;
                }
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
                Copy copy = library.getCopiesOfBook(book).get(rowIndex);
                copy.setCondition(Copy.Condition.values()[cb.getSelectedIndex()]);
            }

            @Override
            public void addTableModelListener(TableModelListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });


        bookDetail.getCopyTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(cb));

        super.bindKeyStrokes();
        bookDetail.getBookDetailPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0),
                "FINISHDETAIL");
        bookDetail.getBookDetailPanel().getActionMap().put("FINISHDETAIL", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                bookDetail.getSaveButton().doClick();
                System.out.println("FINISHDETAIL action");
            }
        });
    }

    protected void cancelPressed() {
        if (getTabDelegate() != null) getTabDelegate().detailControllerDidCancel(this);
    }

    protected void savePressed() {
        if (isValid()) {
            saveChanges();
            if (getTabDelegate() != null) getTabDelegate().detailControllerDidSave(this, true);
        }
    }

    protected void addCopyPressed() {
        if(book != null) {
            book.deleteObserver(this);
            Copy copy = library.createAndAddCopy(book);
            copy.setCondition(Copy.Condition.NEW);
            bookDetail.getCopyTable().updateUI();
            book.addObserver(this);
        }
    }

    public void updateUI() {
        if (!isInSaveProgress) {
            bookDetail.getTitleField().setText(book.getName());
            bookDetail.getAuthorField().setText(book.getAuthor());
            bookDetail.getPublisherField().setText(book.getPublisher());
            if (book.getShelf() != null) bookDetail.getShelfComboBox().setSelectedIndex(book.getShelf().ordinal());
            bookDetail.getCopyTable().updateUI();
        }
    }

    public void saveChanges() {
        isInSaveProgress = true;
        book.setName(bookDetail.getTitleField().getText());
        book.setAuthor(bookDetail.getAuthorField().getText());
        book.setPublisher(bookDetail.getPublisherField().getText());
        book.setShelf((Shelf) bookDetail.getShelfComboBox().getSelectedItem());
        setTitle(bookDetail.getTitleField().getText());
        library.addBook(book);
        isInSaveProgress = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    @Override
    public void setValidations() {
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getTitleField(), "Titel")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getAuthorField(), "Author")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getPublisherField(), "Publisher")));
    }

    @Override
    public void escapeComponent() {
        cancelPressed();
    }

    @Override
    public void setFocus() {
        bookDetail.getTitleField().requestFocusInWindow();
    }
}
