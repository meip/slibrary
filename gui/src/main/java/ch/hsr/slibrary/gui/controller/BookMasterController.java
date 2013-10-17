package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.GUIComponent;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class BookMasterController extends ComponentController implements Observer {


    private BookMaster bookMaster;
    private Library library;



    public BookMasterController(String title, BookMaster component, Library library) {
        super(title);
        this.component = component;
        bookMaster = component;

        this.library = library;

        bookMaster.getBooksAmountLabel().setText(new Integer(library.getBooks().size()).toString());
        bookMaster.getCopyAmountLabel().setText(new Integer(library.getCopies().size()).toString());

    }




    @Override
    public void update(Observable o, Object arg) {



    }
}
