package ch.hsr.slibrary.gui.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class MainMenuBarController extends MenuBarController implements WindowControllerDelegate {


    public static final String MENU_VIEW_ONE_WINDOW = "menuViewOneWindow";
    public static final String MENU_VIEW_TWO_WINDOWS = "menuViewTwoWindows";

    private JMenu viewMenu;
    private JMenu windowMenu;
    private List<ComponentController> windows = new LinkedList<>();
    private List<JMenuItem> windowItems = new LinkedList<>();
    private ButtonGroup windowButtonGroup;

    private WindowController windowController;
    private MainMenuBarControllerDelegate delegate;


    public MainMenuBarController(JMenuBar menuBar, WindowController windowController) {
        super(menuBar);
        this.windowController = windowController;
        initialize();
    }



    private void initialize() {

        final  MainMenuBarController self = this;
        viewMenu = new JMenu("Darstellung");
        getMenuBar().add(viewMenu);

        ButtonGroup toggleViewGroup = new ButtonGroup();

        ActionListener viewChangedActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(delegate != null) delegate.didChangeDisplayMode(self);
            }
        };

        JRadioButtonMenuItem viewItem = new JRadioButtonMenuItem("Als ein Fenster");
        toggleViewGroup.add(viewItem);
        viewItem.addActionListener(viewChangedActionListener);
        viewItem.setName(MENU_VIEW_ONE_WINDOW);
        viewMenu.add(viewItem);

        viewItem = new JRadioButtonMenuItem("Als zwei Fenster");
        toggleViewGroup.add(viewItem);
        viewItem.addActionListener(viewChangedActionListener);
        viewItem.setName(MENU_VIEW_TWO_WINDOWS);
        viewMenu.add(viewItem);


        windowMenu = new JMenu("Fenster");
        getMenuBar().add(windowMenu);
        windowButtonGroup = new ButtonGroup();


    }

    public MainMenuBarControllerDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(MainMenuBarControllerDelegate delegate) {
        this.delegate = delegate;
    }


    public String getSelectedViewItemName() {
        for(int i = 0; i < viewMenu.getItemCount(); ++i) {
            if(viewMenu.getItem(i).isSelected()) {
                return viewMenu.getItem(i).getName();
            }
        }
        return null;
    }

    public void setSelectedViewItemName(String name) {
        for(int i = 0; i < viewMenu.getItemCount(); ++i) {
            JMenuItem item = viewMenu.getItem(i);
            if(item.getName().equals(name)) {
                item.setSelected(true);
                break;
            }
        }
    }


    private void addWindowItem(final ComponentController controller) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(controller.getTitle());
        windowButtonGroup.add(item);
        windowMenu.add(item);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowController.presentControllerAsFrame(controller);
            }
        });
    }

    private void removeWindowItem(ComponentController controller) {
       for(int i = 0; i < windowMenu.getItemCount(); ++i) {
           JMenuItem item = windowMenu.getItem(i);
           if(item.getText().equals(controller.getTitle())) {
               windowMenu.remove(item);
               break;
           }
       }
    }

    private void selectWindowItem(ComponentController controller) {
        for(int i = 0; i < windowMenu.getItemCount(); ++i) {
            JMenuItem item = windowMenu.getItem(i);
            if(item.getText().equals(controller.getTitle())) {
                item.setSelected(true);
                break;
            }
        }
    }

    @Override
    public void windowDidOpenController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowWillCloseController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        if(windows.contains(controller)) {
            windows.remove(controller);
            removeWindowItem(controller);
        }
    }

    @Override
    public void windowDidActivateController(WindowController windowController, ComponentController controller) {
        selectWindowItem(controller);
    }

    @Override
    public void windowDidDeactivateController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void didAddWindowController(WindowController windowController, ComponentController controller) {
        windows.add(controller);
        addWindowItem(controller);
    }
}
