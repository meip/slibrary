package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.WindowController;
import ch.hsr.slibrary.gui.controller.system.WindowControllerDelegate;
import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class MDSeparatedTabbedController extends MDTabbedController implements WindowControllerDelegate {




    public MDSeparatedTabbedController(WindowController windowController, ComponentController masterController, String title) {
        super(masterController, title, windowController);

        windowController.presentControllerAsFrame(masterController, JFrame.DISPOSE_ON_CLOSE, WindowController.getBoundsForWindowPosition(WindowBounds.WINDOW_POSITION_FILL_LEFT));
        windowController.addDelegate(this);
    }

    public MDSeparatedTabbedController(WindowController windowController, ComponentController masterController, String title, ComponentController replaceController) {
        super(masterController, title, windowController);
        windowController.replaceControllerInFrame(replaceController, masterController);
        windowController.arrangeControllerWithPosition(masterController, WindowBounds.WINDOW_POSITION_FILL_LEFT);
        windowController.addDelegate(this);
    }

    @Override
    protected void doAddDetailController(ComponentController detailController) {
        getTabController().addController(detailController);
        if(!windowController.containsController(getTabController())) {
            Rectangle bounds = WindowController.getBoundsForWindowPosition(WindowBounds.WINDOW_POSITION_FILL_RIGHT);
            bounds.setLocation(bounds.getSize().width / 2, bounds.getLocation().y);
            windowController.presentControllerAsFrame(getTabController(), JFrame.DISPOSE_ON_CLOSE, bounds);
            windowController.arrangeControllerWithPosition(getTabController(), WindowBounds.WINDOW_POSITION_FILL_RIGHT);
        }
    }

    public ComponentController getWindowedController() {
        return masterController;
    }

    @Override
    protected void doInsertDetailControllerAt(ComponentController detailController, int index) {
        getTabController().insertControllerAt(detailController, index);
    }

    @Override
    protected void doRemoveDetailController(ComponentController detailController) {
        getTabController().removeController(detailController);
        if(getTabController().getControllers().size() == 0) {
            windowController.dismissController(getTabController());
        }
    }

    @Override
    protected void doRemoveAllDetailControllers() {
        getTabController().removeAllControllers();
        windowController.dismissController(getTabController());
    }

    @Override
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        if(controller == getTabController()) {
            removeAllDetailControllers();
        }
    }

    public void dismiss() {
        windowController.removeDelegate(this);
        windowController.dismissController(getTabController());
    }
}
