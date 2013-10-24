package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.TabGUIComponent;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 24.10.13
 * Time: 21:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class MDTabbedController extends MasterDetailController implements WindowControllerDelegate {

    protected WindowController windowController;
    protected TabController _tabController;

    public MDTabbedController(ComponentController masterController, String title, WindowController windowController) {
        super(masterController, title);
        this.windowController = windowController;
    }


    @Override
    public void windowDidCloseController(WindowController windowController, ComponentController controller) {
        if(controller == getTabController()) {
            removeAllDetailControllers();
        }
    }

    protected TabController getTabController() {
        if(_tabController == null) _tabController = new TabController("Details", new TabGUIComponent());
        return _tabController;
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
