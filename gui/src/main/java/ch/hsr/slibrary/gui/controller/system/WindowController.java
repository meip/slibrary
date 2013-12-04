package ch.hsr.slibrary.gui.controller.system;


import ch.hsr.slibrary.gui.util.StringUtil;
import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WindowController implements ComponentControllerDelegate {


    private Set<WindowControllerDelegate> delegates = new HashSet<>();
    private Map<ComponentController, JFrame> controllerFrames = new HashMap<>();
    private MenuBarController menuBarController;


    public void presentControllerAsFrame(ComponentController controller) {
        presentControllerAsFrame(controller, JFrame.DISPOSE_ON_CLOSE, null);
    }

    public void presentControllerAsFrame(final ComponentController controller, int closeOperation, Rectangle windowBounds ) {
        if(!controllerFrames.containsKey(controller)) {
            JFrame frame = new JFrame(controller.getTitle());
            frame.setContentPane(controller.getComponent().getContainer());
            frame.setDefaultCloseOperation(closeOperation);
            frame.setMinimumSize(controller.getComponent().getMinimumSize());
            if(windowBounds != null) {
                frame.setBounds(windowBounds);
            }
            frame.setVisible(true);
            frame.setMinimumSize(new Dimension(1440, 768));
            addWindowListenersToFrame(frame, controller);

            if(menuBarController != null) {
                frame.setJMenuBar(menuBarController.getMenuBar());
            }

            for(WindowControllerDelegate delegate : delegates) {
                delegate.didAddWindowController(this, controller);
            }

            controllerFrames.put(controller, frame);
            controller.setDelegate(this);
        } else {
            bringToFront(controller);
        }
    }

    private void addWindowListenersToFrame(JFrame frame, final ComponentController controller) {
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
                    controllerFrames.get(controller).setJMenuBar(menuBarController.getMenuBar());
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
    }


    public void addDelegate(WindowControllerDelegate delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(WindowControllerDelegate delegate) {
        delegates.remove(delegate);
    }


    public void dismissController(ComponentController controller) {
        if(controllerFrames.containsKey(controller)) {
            controller.setDelegate(this);
            JFrame frame = controllerFrames.get(controller);
            controllerFrames.remove(controller);
            frame.dispose();
        }
    }

    public void dismissCurrentController() {
        ComponentController controllerToDismiss = null;
        for(ComponentController controller : controllerFrames.keySet()) {
            if(controllerFrames.get(controller).isActive()) {
                controllerToDismiss = controller;
                break;
            }
        }
        if(controllerToDismiss != null) {
            dismissController(controllerToDismiss);
        }
    }

    public boolean containsController(ComponentController controller) {
        return controllerFrames.containsKey(controller);
    }

    public void bringToFront(ComponentController controller) {
        if(controllerFrames.containsKey(controller)) {
            controllerFrames.get(controller).toFront();
            controllerFrames.get(controller).setJMenuBar(menuBarController.getMenuBar());
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
            frame.setJMenuBar(menuBarController.getMenuBar());
            addWindowListenersToFrame(frame, newController);
            controllerFrames.remove(oldController);
            controllerFrames.put(newController, frame);
            for(WindowControllerDelegate delegate : delegates) {
                delegate.windowDidReplaceController(oldController, newController);
            }
        }
    }


    public void setMenuBarControllers(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
        for(JFrame frame : controllerFrames.values()) {
            frame.setJMenuBar(menuBarController.getMenuBar());
        }
    }

    public MenuBarController getMenuBarController() {
        return menuBarController;
    }

    @Override
    public void controllerDidChangeTitle(ComponentController controller) {
        if(controllerFrames.containsKey(controller)){
            controllerFrames.get(controller).setTitle(StringUtil.trimToWordsWithMaxLength(controller.getTitle(), 5, 25));
        }
    }
}
