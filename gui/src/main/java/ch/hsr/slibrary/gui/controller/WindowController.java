package ch.hsr.slibrary.gui.controller;


import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

public class WindowController {


    private Set<WindowControllerDelegate> delegates = new HashSet<>();
    private Map<ComponentController, JFrame> controllerFrames = new HashMap<>();
    private MenuBarController defaultMenuBarController;
    private Map<ComponentController, MenuBarController> controllerMenuMapping = new HashMap<>();


    public void presentControllerAsFrame(ComponentController controller) {
        presentControllerAsFrame(controller, JFrame.DISPOSE_ON_CLOSE, null);
    }

    public void presentControllerAsFrame(final ComponentController controller, int closeOperation, Rectangle windowBounds ) {


        if(!controllerFrames.containsKey(controller)) {
            JFrame frame = new JFrame(controller.getTitle());
            frame.setContentPane(controller.getComponent().getContainer());
            frame.setDefaultCloseOperation(closeOperation);
            frame.setMinimumSize(controller.getComponent().getMinimumSize());
            frame.setBounds(windowBounds != null ? windowBounds : new Rectangle(getScreenBounds().x, getScreenBounds().y, frame.getMinimumSize().width, frame.getMinimumSize().height));
            frame.setVisible(true);
            final WindowController self = this;
            frame.addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {
                    for(WindowControllerDelegate delegate : delegates) {
                        delegate.windowDidOpenController(self, controller);
                    }
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    for(WindowControllerDelegate delegate : delegates) {
                        delegate.windowWillCloseController(self, controller);
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    controllerFrames.remove(controller);
                    for(WindowControllerDelegate delegate : delegates) {
                        delegate.windowDidCloseController(self, controller);
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowDeiconified(WindowEvent e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }

                @Override
                public void windowActivated(WindowEvent e) {
                    if(controllerFrames.containsKey(controller)) {
                        controllerFrames.get(controller).setJMenuBar(defaultMenuBarController.getMenuBar());
                    }
                    for(WindowControllerDelegate delegate : delegates) {
                        delegate.windowDidActivateController(self, controller);
                    }
                }

                @Override
                public void windowDeactivated(WindowEvent e) {
                    for(WindowControllerDelegate delegate : delegates) {
                        delegate.windowDidDeactivateController(self, controller);
                    }
                }
            });
            controller.setWindowController(this);
            if(defaultMenuBarController != null) {
                frame.setJMenuBar(defaultMenuBarController.getMenuBar());
                //controllerMenuMapping.put(controller, defaultMenuBarController);
            }
            controllerFrames.put(controller, frame);
            for(WindowControllerDelegate delegate : delegates) {
                delegate.didAddWindowController(this, controller);
            }

        } else {
            bringToFront(controller);
        }
    }


    public void addDelegate(WindowControllerDelegate delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(WindowControllerDelegate delegate) {
        delegates.remove(delegate);
    }


    public void dismissController(ComponentController controller) {
        if(controllerFrames.containsKey(controller)) {
            JFrame frame = controllerFrames.get(controller);
            controllerFrames.remove(controller);
            frame.dispose();
        }
    }

    public boolean containsController(ComponentController controller) {
        return controllerFrames.containsKey(controller);
    }

    public void bringToFront(ComponentController controller) {
        if(controllerFrames.containsKey(controller)) {
            controllerFrames.get(controller).toFront();
        }
    }


    public void arrangeControllerWithPosition(ComponentController controller, int windowPosition) {
        JFrame frame = controllerFrames.get(controller);
        if(frame != null) {
            frame.setBounds(getBoundsForWindowPosition(windowPosition));
        }
    }

    public static Rectangle getScreenBounds() {
       return GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    }

    public static Rectangle getBoundsForWindowPosition(int position) {
        Rectangle bounds = getScreenBounds();
        switch (position) {
            case WindowBounds.WINDOW_POSITION_FILL_SCREEN:
                bounds = getScreenBounds();
                break;

            case WindowBounds.WINDOW_POSITION_FILL_LEFT:
                bounds.setSize((int)getScreenBounds().getWidth() / 2, (int) getScreenBounds().getHeight());
                bounds.setLocation(getScreenBounds().getLocation().x, getScreenBounds().getLocation().y);
                break;

            case WindowBounds.WINDOW_POSITION_FILL_RIGHT:
                bounds.setSize((int)getScreenBounds().getWidth() / 2, (int) getScreenBounds().getHeight());
                bounds.setLocation(getScreenBounds().getLocation().x + (int)getScreenBounds().getWidth() / 2, getScreenBounds().getLocation().y);
                break;

            case WindowBounds.WINDOW_POSITION_RIGHT_TOP:
                bounds.setSize((int)getScreenBounds().getWidth() / 2, (int) getScreenBounds().getHeight() / 2);
                bounds.setLocation(getScreenBounds().getLocation().x + (int)getScreenBounds().getWidth() / 2, getScreenBounds().getLocation().y);
                break;
        }
        return bounds;
    }


    public void replaceControllerInFrame(ComponentController oldController, ComponentController newController) {
        if(controllerFrames.containsKey(oldController)) {
            JFrame frame = controllerFrames.get(oldController);
            frame.setTitle(newController.getTitle());
            frame.setContentPane(newController.getComponent().getContainer());
            frame.setJMenuBar(defaultMenuBarController.getMenuBar());
            controllerFrames.remove(oldController);
            controllerFrames.put(newController, frame);
        }
    }

    public void setMenuBarForController(MenuBarController menuBarController, ComponentController controller) {
        if(controllerFrames.containsKey(controller)) {
            controllerFrames.get(controller).setJMenuBar(menuBarController.getMenuBar());
            controllerMenuMapping.put(controller, menuBarController);
        }
    }

    public void setMenuBarForAllControllers(MenuBarController menuBarController) {
        defaultMenuBarController = menuBarController;
        for(JFrame frame : controllerFrames.values()) {
            frame.setJMenuBar(menuBarController.getMenuBar());
        }
        for(ComponentController controller : controllerFrames.keySet()) {
            controllerMenuMapping.put(controller, menuBarController);
        }
    }
}
