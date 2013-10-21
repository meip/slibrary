package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.TabGUIComponent;
import ch.hsr.slibrary.gui.util.WindowBounds;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public class JFrameSeparatedWithTabsMasterDetailController extends MasterDetailController{


    private WindowController windowController;
    private TabController _tabController;

    public JFrameSeparatedWithTabsMasterDetailController(WindowController windowController, ComponentController masterController) {
        super(masterController);
        this.windowController = windowController;
        windowController.presentControllerAsFrame(masterController, JFrame.EXIT_ON_CLOSE);
        windowController.arrangeControllerWithPosition(masterController, WindowBounds.WINDOW_POSITION_FILL_LEFT);
    }

    private TabController getTabController() {
        if(_tabController == null) _tabController = new TabController(new TabGUIComponent());
        return _tabController;
    }

    @Override
    protected void doAddDetailController(ComponentController detailController) {
        getTabController().addController(detailController);
        if(!windowController.containsController(getTabController())) {
            windowController.presentControllerAsFrame(getTabController());
            windowController.arrangeControllerWithPosition(getTabController(), WindowBounds.WINDOW_POSITION_FILL_RIGHT);
        }
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
    protected void doSetSelectedDetailController(ComponentController detailController) {
        getTabController().showController(detailController);
        windowController.bringToFront(getTabController());
    }
}
