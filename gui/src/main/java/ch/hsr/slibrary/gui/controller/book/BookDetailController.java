package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.controller.system.ValidatableComponentController;
import ch.hsr.slibrary.gui.controller.listener.EscapeKeyListener;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.gui.validation.EmptyTextValidation;
import ch.hsr.slibrary.gui.validation.ValidationRule;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Copy;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Shelf;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                if (getTabDelegate() != null) getTabDelegate().detailControllerDidCancel(self);
            }
        });

        bookDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                if (isValid()) {
                    saveChanges();
                    if (getTabDelegate() != null) getTabDelegate().detailControllerDidSave(self, true);
                }
            }
        });

        bookDetail.getContainer().addKeyListener(new EscapeKeyListener() {
            @Override
            public void escapeAction() {
                if (getTabDelegate() != null) getTabDelegate().detailControllerDidCancel(self);
            }
        });

        bookDetail.getCopyList().setModel(new ListModel() {
            @Override
            public int getSize() {
                return library.getCopiesOfBook(book).size();
            }

            @Override
            public Object getElementAt(int index) {
                Copy copy = library.getCopiesOfBook(book).get(index);
                return "#" + copy.getInventoryNumber();
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

    }

    public void updateUI() {
        bookDetail.getTitleField().setText(book.getName());
        bookDetail.getAuthorField().setText(book.getAuthor());
        bookDetail.getPublisherField().setText(book.getPublisher());
        if (book.getShelf() != null) bookDetail.getShelfComboBox().setSelectedIndex(book.getShelf().ordinal());
    }

    public void saveChanges() {
        setTitle(bookDetail.getTitleField().getText());
        book.setName(bookDetail.getTitleField().getText());
        book.setAuthor(bookDetail.getAuthorField().getText());
        book.setPublisher(bookDetail.getPublisherField().getText());
        book.setShelf((Shelf) bookDetail.getShelfComboBox().getSelectedItem());
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void setValidations() {
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getTitleField(), "Titel")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getAuthorField(), "Author")));
        validationRules.add(new ValidationRule(new EmptyTextValidation(bookDetail.getPublisherField(), "Publisher")));
    }
}
