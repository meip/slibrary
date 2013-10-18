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

    public void presentControllerAsFrame(final ComponentController controller, int closeOperation ) {


        if(!controllerFrames.containsKey(controller)) {
            JFrame frame = new JFrame(controller.getTitle());
            frame.setContentPane(controller.getComponent().getContainer());
            frame.setDefaultCloseOperation(closeOperation);
            frame.pack();
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
            controllerFrames.put(controller, frame);
        } else {
            controllerFrames.get(controller).toFront();
        }
    }


    public void addDelegate(WindowControllerDelegate delegate) {
        delegates.add(delegate);
    }

    public void removeDelegate(WindowControllerDelegate delegate) {
        delegates.remove(delegate);
    }

    public void presentControllerAsFrame(ComponentController controller) {

        presentControllerAsFrame(controller, JFrame.DISPOSE_ON_CLOSE);
    }

    public void dismissController(ComponentController controller) {
        JFrame frame = controllerFrames.get(controller);
        frame.dispose();
        controllerFrames.remove(controller);
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
            Toolkit tk = Toolkit.getDefaultToolkit();
            Dimension screenSize = tk.getScreenSize();

            switch (windowPosition) {
                case WindowBounds.WINDOW_POSITION_FILL_LEFT:
                    frame.setSize((int) screenSize.getWidth() / 2, (int) screenSize.getHeight());
                    frame.setLocation(0,0);
                    break;

                case WindowBounds.WINDOW_POSITION_FILL_RIGHT:
                    frame.setSize((int) screenSize.getWidth() / 2, (int) screenSize.getHeight());
                    frame.setLocation((int) screenSize.getWidth()/2,0);
                    break;

                case WindowBounds.WINDOW_POSITION_RIGHT_TOP:
                    frame.setSize((int) screenSize.getWidth() / 2, frame.getHeight());
                    frame.setLocation((int) screenSize.getWidth()/2,0);
                    break;
            }
        }
    }






}
