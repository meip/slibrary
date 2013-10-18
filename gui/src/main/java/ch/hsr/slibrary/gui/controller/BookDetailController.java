package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Shelf;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * User: p1meier
 * Date: 10.10.13
 */
public class BookDetailController extends ComponentController implements Observer {

    private BookDetail bookDetail;
    private Book book;
    private Library library;
    private BookDetailControllerDelegate delegate;

    public BookDetailController(String title, BookDetail component, Book book, Library library) {
        super(title);
        this.component = component;
        this.bookDetail = component;
        this.book = book;
        this.library = library;
        this.setTitle(title);

        initialize();
    }

    @Override
    public void initialize() {
        initializeUI();
        updateUI();
    }

    public BookDetailControllerDelegate getDelegate() {
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
                if(getDelegate() != null) getDelegate().detailControllerDidCancel(self);
            }
        });

        bookDetail.getContainer().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if(getDelegate() != null) getDelegate().detailControllerDidCancel(self);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    public void updateUI() {
        bookDetail.getTitleField().setText(book.getName());
        bookDetail.getAuthorField().setText(book.getAuthor());
        bookDetail.getPublisherField().setText(book.getPublisher());
        bookDetail.getShelfComboBox().setSelectedIndex(book.getShelf().ordinal());
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
