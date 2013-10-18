package ch.hsr.slibrary.gui.controller;


import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 17.10.13
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class WindowController {


    private Map<ComponentController, JFrame> controllerFrames = new HashMap<>();

    public void presentControllerAsFrame(ComponentController controller, int closeOperation ) {


        if(!controllerFrames.containsKey(controller)) {
            JFrame frame = new JFrame(controller.getTitle());
            frame.setContentPane(controller.getComponent().getContainer());
            frame.setDefaultCloseOperation(closeOperation);
            frame.pack();
            frame.setVisible(true);
            controller.setWindowController(this);
            controllerFrames.put(controller, frame);
        } else {
            controllerFrames.get(controller).toFront();
        }
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

    public void arrangeControllerWithPosition(ComponentController controller) {

    }

}
