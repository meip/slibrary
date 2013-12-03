package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MenuBarController;
import ch.hsr.slibrary.gui.controller.system.WindowController;
import ch.hsr.slibrary.gui.controller.system.WindowControllerDelegate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class MainMenuBarController extends MenuBarController implements WindowControllerDelegate {


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

        final MainMenuBarController self = this;

        fileMenu = new JMenu("Ablage");
        getMenuBar().add(fileMenu);
        JMenuItem windowCloseItem = new JRadioButtonMenuItem("Fenster schliessen");
        windowCloseItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowController.dismissCurrentController();
            }
        });
        windowCloseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileMenu.add(windowCloseItem);

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
        if (controllerItems.containsKey(controller)) {
            windowMenu.remove(controllerItems.get(controller));
            windowButtonGroup.remove(controllerItems.get(controller));
            controllerItems.remove(controller);
        }
    }

    private void selectWindowItem(ComponentController controller) {
        if (controllerItems.containsKey(controller)) {
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
