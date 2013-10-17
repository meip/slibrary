package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.BookMaster;
import ch.hsr.slibrary.gui.form.GUIComponent;
import ch.hsr.slibrary.spa.Library;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class BookMasterController extends ComponentController implements Observer {


    private BookMaster bookMaster;
    private Library library;



    public BookMasterController(String title, BookMaster component, Library _library) {
        super(title);
        this.component = component;
        bookMaster = component;

        this.library = _library;

        bookMaster.getBooksAmountLabel().setText(new Integer(library.getBooks().size()).toString());
        bookMaster.getCopyAmountLabel().setText(new Integer(library.getCopies().size()).toString());

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

    }




    @Override
    public void update(Observable o, Object arg) {



    }
}
