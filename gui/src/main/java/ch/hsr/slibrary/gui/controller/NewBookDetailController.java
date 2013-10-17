package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.spa.Library;


public class NewBookDetailController extends ComponentController {

    private Library library;
    private BookDetail bookDetail;

    public NewBookDetailController(String title, BookDetail component, Library library) {
        super(title);
        this.component = component;
        this.bookDetail = component;
        this.library = library;

    }
}
