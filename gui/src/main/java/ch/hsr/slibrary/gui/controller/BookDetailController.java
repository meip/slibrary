package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

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

    public BookDetailController(String title, BookDetail component, Book book, Library library) {
        super(title);
        this.component = component;
        this.bookDetail = component;
        this.book = book;
        this.library = library;


    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
