package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 24.10.13
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class MDStandaloneController extends MasterDetailController implements WindowControllerDelegate{

    private WindowController windowController;

    public MDStandaloneController(WindowController windowController, ComponentController masterController, String title) {
        super(masterController, title);
        this.windowController = windowController;

        windowController.presentControllerAsFrame(masterController, JFrame.DISPOSE_ON_CLOSE, WindowController.getBoundsForWindowPosition(WindowBounds.WINDOW_POSITION_FILL_LEFT));
        windowController.addDelegate(this);
    }

    public MDStandaloneController(WindowController windowController, ComponentController masterController, String title, ComponentController replaceController) {
        super(masterController, title);
        this.windowController = windowController;

        windowController.replaceControllerInFrame(replaceController, masterController);
        windowController.arrangeControllerWithPosition(masterController, WindowBounds.WINDOW_POSITION_FILL_LEFT);
        windowController.addDelegate(this);
    }

    @Override
    protected void doAddDetailController(ComponentController detailController) {
        windowController.presentControllerAsFrame(detailController);
        windowController.arrangeControllerWithPosition(detailController, WindowBounds.WINDOW_POSITION_FILL_RIGHT);
    }

    @Override
    protected void doInsertDetailControllerAt(ComponentController detailController, int index) {
        windowController.presentControllerAsFrame(detailController);
    }

    @Override
    protected void doRemoveDetailController(ComponentController detailController) {
        windowController.dismissController(detailController);
    }

    @Override
    protected void doRemoveAllDetailControllers() {
        for(ComponentController detailController:getDetailControllers()) {
            windowController.dismissController(detailController);
        }
    }

    @Override
    protected void doSetSelectedDetailController(ComponentController detailController) {
        windowController.bringToFront(detailController);
    }

    @Override
    public ComponentController getWindowedController() {
        return masterController;
    }

    @Override
    public void dismiss() {
        windowController.removeDelegate(this);
        removeAllDetailControllers();
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
        removeDetailController(controller);
    }

    @Override
    public void windowDidActivateController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDidDeactivateController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void didAddWindowController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowDidReplaceController(ComponentController oldController, ComponentController newController) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
