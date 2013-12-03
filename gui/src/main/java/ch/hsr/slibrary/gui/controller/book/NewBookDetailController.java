package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewBookDetailController extends BookDetailController {

    public NewBookDetailController(String title, BookDetail component, Library library) {
        super(title, component, new Book(""), library);
    }

    @Override
    protected void cancelPressed() {
        library.removeBook(book);
        super.cancelPressed();
    }
}
