package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.*;
import ch.hsr.slibrary.gui.form.VerticalSplitComponent;
import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class MDIntegratedTabbedController extends MDTabbedController implements WindowControllerDelegate {

    private TabController _tabController;
    private SplitController splitController;

    public MDIntegratedTabbedController(WindowController windowController, ComponentController masterController, String title, ComponentController replaceController) {
        super(masterController, title, windowController);
        initializeSplitController();

        windowController.replaceControllerInFrame(replaceController, splitController);
        windowController.arrangeControllerWithPosition(splitController, WindowBounds.WINDOW_POSITION_FILL_SCREEN);
        windowController.addDelegate(this);
    }

    public MDIntegratedTabbedController(WindowController windowController, ComponentController masterController, String title) {

        super(masterController, title, windowController);
        initializeSplitController();

        windowController.presentControllerAsFrame(splitController, JFrame.DISPOSE_ON_CLOSE, WindowController.getBoundsForWindowPosition(WindowBounds.WINDOW_POSITION_FILL_SCREEN));
        windowController.addDelegate(this);
    }

    private void initializeSplitController() {
        splitController = new SplitController(getTitle(), new VerticalSplitComponent());
        splitController.setFirstController(masterController);
        splitController.setSecondController(null);
    }


    public ComponentController getWindowedController() {
        return splitController;
    }

    @Override
    protected void doAddDetailController(ComponentController detailController) {
        getTabController().addController(detailController);
        if(splitController.getSecondController() == null) {
            splitController.setSecondController(getTabController());
        }
    }

    @Override
    protected void doInsertDetailControllerAt(ComponentController detailController, int index) {
        getTabController().insertControllerAt(detailController, index);
        if(splitController.getSecondController() == null) {
            splitController.setSecondController(getTabController());
        }
    }

    @Override
    protected void doRemoveDetailController(ComponentController detailController) {
        getTabController().removeController(detailController);
        if(getTabController().getControllers().size() == 0) {
            splitController.setSecondController(null);
        }
    }

    @Override
    protected void doRemoveAllDetailControllers() {
        getTabController().removeAllControllers();
        splitController.setSecondController(null);
    }

    @Override
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        if(controller == getTabController()) {
            removeAllDetailControllers();
        }
    }

    public void dismiss() {
       windowController.removeDelegate(this);
    }
}
