package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.TabGUIComponent;
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
public class SingleFrameTabMDController extends MasterDetailController implements WindowControllerDelegate{


    private WindowController windowController;
    private TabController _tabController;
    private SplitController splitController;

    public SingleFrameTabMDController(WindowController windowController, ComponentController masterController, String title, ComponentController replaceController) {
        super(masterController);
        this.windowController = windowController;
        splitController = new SplitController(title, new VerticalSplitComponent());
        splitController.setFirstController(masterController);
        splitController.setSecondController(null);

        windowController.replaceControllerInFrame(replaceController, splitController);
        windowController.arrangeControllerWithPosition(splitController, WindowBounds.WINDOW_POSITION_FILL_SCREEN);
        windowController.addDelegate(this);
    }

    public SingleFrameTabMDController(WindowController windowController, ComponentController masterController, String title) {

        super(masterController);
        this.windowController = windowController;
        splitController = new SplitController(title, new VerticalSplitComponent());
        splitController.setFirstController(masterController);
        splitController.setSecondController(null);

        windowController.presentControllerAsFrame(splitController, JFrame.DISPOSE_ON_CLOSE, WindowController.getBoundsForWindowPosition(WindowBounds.WINDOW_POSITION_FILL_SCREEN));
        windowController.arrangeControllerWithPosition(splitController, WindowBounds.WINDOW_POSITION_FILL_SCREEN);
        windowController.addDelegate(this);
    }


    public ComponentController getWindowedController() {
        return splitController;
    }

    private TabController getTabController() {
        if(_tabController == null) _tabController = new TabController(new TabGUIComponent());
        return _tabController;
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
    protected void doSetSelectedDetailController(ComponentController detailController) {
        getTabController().showController(detailController);
        windowController.bringToFront(getTabController());
    }

    @Override
    public void windowDidOpenController(WindowController windowController, ComponentController controller) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void windowWillCloseController(WindowController windowController, ComponentController controller) {

    }

    @Override
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        if(controller == getTabController()) {
            removeAllDetailControllers();
        }
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

    public void dismiss() {
       //windowController.dismissController(splitController);
       windowController.removeDelegate(this);
    }
}
