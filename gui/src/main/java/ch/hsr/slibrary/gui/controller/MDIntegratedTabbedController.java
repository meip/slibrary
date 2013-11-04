package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.controller.system.*;
import ch.hsr.slibrary.gui.form.GUIComponent;
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
public class MDIntegratedTabbedController extends MasterDetailController{

    private TabController _tabController;
    private SplitController splitController;




    public MDIntegratedTabbedController(ComponentController masterController, String title) {

        super(masterController, title);
        initializeSplitController();
    }

    private void initializeSplitController() {
        splitController = new SplitController(getTitle(), new VerticalSplitComponent());
        splitController.setFirstController(masterController);
        splitController.setSecondController(null);
    }

    protected TabController getTabController() {
        if(_tabController == null) _tabController = new TabController("Details", new TabGUIComponent());
        return _tabController;
    }

    public ComponentController getWindowedController() {
        return splitController;
    }

    @Override
    public GUIComponent getComponent() {
        return splitController.getComponent();
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
    }

    public void dismiss() {

    }


}
