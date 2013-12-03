package ch.hsr.slibrary.gui.controller.book;

import ch.hsr.slibrary.gui.form.BookDetail;
import ch.hsr.slibrary.spa.Book;
import ch.hsr.slibrary.spa.Library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NewBookDetailController extends BookDetailController {

    public NewBookDetailController(String title, BookDetail component, Library library) {
        super(title, component, library.createAndAddBook(""), library);
        initializeSaveButton();
    }

    @Override
    protected void cancelPressed() {
        library.removeBook(book);
        super.cancelPressed();
    }

    public void initializeSaveButton() {
        final BookDetailController self = this;
        bookDetail.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isValid()) {
                    book = library.createAndAddBook(bookDetail.getTitleField().getText());
                    saveChanges();
                    if (getTabDelegate() != null) getTabDelegate().detailControllerDidSave(self, true);
                }
            }
        });
    }
}
