package ch.hsr.slibrary.gui.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainMenuBarController extends MenuBarController implements WindowControllerDelegate {


    public static final String MENU_VIEW_INTEGRATED_WINDOW = "menuViewIntegratedWindow";
    public static final String MENU_VIEW_SEPARATED_WINDOWS = "menuViewSeparatedWindows";
    public static final String MENU_VIEW_STANDALONE_WINDOWS = "menuViewStandaloneWindows";

    private JMenu viewMenu;
    private JMenu windowMenu;
    private JMenu fileMenu;
    private ButtonGroup windowButtonGroup;
    private Map<ComponentController, JMenuItem> controllerItems = new HashMap<>();
    private JMenuItem currentViewItem;

    private WindowController windowController;
    private MainMenuBarControllerDelegate delegate;


    public MainMenuBarController(JMenuBar menuBar, WindowController windowController) {
        super(menuBar);
        this.windowController = windowController;
        initialize();
    }



    private void initialize() {

        final  MainMenuBarController self = this;


        fileMenu = new JMenu("Ablage");
        getMenuBar().add(fileMenu);
        JMenuItem fileItem = new JRadioButtonMenuItem("Fenster schliessen");
        fileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowController.dismissCurrentController();
            }
        });
        fileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileMenu.add(fileItem);


        viewMenu = new JMenu("Darstellung");
        getMenuBar().add(viewMenu);

        ButtonGroup toggleViewGroup = new ButtonGroup();

        ActionListener viewChangedActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() != currentViewItem) {
                    currentViewItem = (JMenuItem) e.getSource();
                    if(delegate != null) delegate.didChangeDisplayMode(self);
                } else {
                    currentViewItem.setSelected(true);
                }

            }
        };

        JRadioButtonMenuItem viewItem = new JRadioButtonMenuItem("Alle Fenster zusammenfassen");
        toggleViewGroup.add(viewItem);
        viewItem.addActionListener(viewChangedActionListener);
        viewItem.setName(MENU_VIEW_INTEGRATED_WINDOW);
        viewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        viewMenu.add(viewItem);

        viewItem = new JRadioButtonMenuItem("Details zusammenfassen");
        toggleViewGroup.add(viewItem);
        viewItem.addActionListener(viewChangedActionListener);
        viewItem.setName(MENU_VIEW_SEPARATED_WINDOWS);
        viewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        viewMenu.add(viewItem);

        viewItem = new JRadioButtonMenuItem("Einzelne Fenster");
        toggleViewGroup.add(viewItem);
        viewItem.addActionListener(viewChangedActionListener);
        viewItem.setName(MENU_VIEW_STANDALONE_WINDOWS);
        viewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,  Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
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
                currentViewItem = item;
                break;
            }
        }
    }


    private void addWindowItem(final ComponentController controller) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(controller.getTitle());
        windowButtonGroup.add(item);
        windowMenu.add(item);
        controllerItems.put(controller, item);
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowController.presentControllerAsFrame(controller);
            }
        });

    }

    private void removeWindowItem(ComponentController controller) {
        if(controllerItems.containsKey(controller)) {
            windowMenu.remove(controllerItems.get(controller));
            windowButtonGroup.remove(controllerItems.get(controller));
            controllerItems.remove(controller);
        }
    }

    private void selectWindowItem(ComponentController controller) {
       /* for(int i = 0; i < windowMenu.getItemCount(); ++i) {
            JMenuItem item = windowMenu.getItem(i);
            if(item.getText().equals(controller.getTitle())) {
                item.setSelected(true);
                break;
            }
        }*/
        if(controllerItems.containsKey(controller)) {
            controllerItems.get(controller).setSelected(true);
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
        removeWindowItem(controller);
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
        addWindowItem(controller);
    }

    @Override
    public void windowDidReplaceController(ComponentController oldController, ComponentController newController) {
        removeWindowItem(oldController);
        addWindowItem(newController);
    }

}
