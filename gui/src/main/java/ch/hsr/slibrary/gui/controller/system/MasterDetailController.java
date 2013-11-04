package ch.hsr.slibrary.gui.controller.system;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class MasterDetailController extends ComponentController{


    private MasterDetailControllerDelegate delegate;
    private List<ComponentController> detailControllers = new LinkedList<>();
    private ComponentController selectedController;
    protected ComponentController masterController;

    public MasterDetailController(ComponentController masterController, String title) {
        super(title);
        this.masterController = masterController;
    }

    public void addDetailController(ComponentController detailController){
        if(!containsDetailController(detailController)) {
            detailControllers.add(detailController);
            doAddDetailController(detailController);
        }
        if(detailControllers.size() == 1) {
            setSelectedDetailController(detailController);
        }
    }

    public void insertDetailControllerAt(ComponentController detailController, int index) {
        if(!containsDetailController(detailController)) {
            detailControllers.add(index, detailController);
            doInsertDetailControllerAt(detailController, index);
        }
    }

    public void setDetailControllers(List<ComponentController> detailControllers) {
        this.detailControllers = detailControllers;
        doRemoveAllDetailControllers();
        for(ComponentController controller : detailControllers) {
            doAddDetailController(controller);
        }
    }

    public List<ComponentController> getDetailControllers() {
        return detailControllers;
    }

    public void removeDetailController(ComponentController detailController) {
        if(containsDetailController(detailController)) {
            if(delegate != null) delegate.willRemoveDetailController(detailController);
            detailControllers.remove(detailController);
            doRemoveDetailController(detailController);
            if(delegate != null) delegate.didRemoveDetailController(detailController);
        }
    }

    public void removeDetailControllerAt(int index) {
        if(index >= 0 && index < detailControllers.size()) {
            ComponentController controller = detailControllers.get(index);
            if(delegate != null) delegate.willRemoveDetailController(controller);
            detailControllers.remove(index);
            doRemoveDetailController(controller);
            if(delegate != null) delegate.didRemoveDetailController(controller);
        }
    }

    public void removeAllDetailControllers() {
        for(ComponentController controller : detailControllers) {
            if(delegate != null) delegate.willRemoveDetailController(controller);
        }
        doRemoveAllDetailControllers();
        for(ComponentController controller : detailControllers) {
            if(delegate != null) delegate.didRemoveDetailController(controller);
        }
        setDetailControllers(new LinkedList<ComponentController>());
    }

    public void setSelectedDetailController(ComponentController detailController) {
        if(delegate != null) delegate.willSelectDetailController(detailController);
        selectedController = detailController;
        doSetSelectedDetailController(detailController);
        if(delegate != null) delegate.didSelectDetailController(detailController);
    }

    public void setSelectedDetailControllerAt(int index) {
        if(index >= 0 && index < detailControllers.size()) {
            setSelectedDetailController(detailControllers.get(index));
        }
    }

    public ComponentController getSelectedDetailController() {
        return selectedController;
    }

    public ComponentController getDetailControllerAt(int index) {
        return detailControllers.get(index);
    }

    public int indexOfDetailController(ComponentController detailController) {
        return detailControllers.indexOf(detailController);
    }

    public boolean containsDetailController(ComponentController detailController) {
        return detailControllers.contains(detailController);
    }

    public MasterDetailControllerDelegate getTabDelegate() {
        return delegate;
    }

    public void setDelegate(MasterDetailControllerDelegate delegate) {
        this.delegate = delegate;
    }

    abstract protected void doAddDetailController(ComponentController detailController);
    abstract protected void doInsertDetailControllerAt(ComponentController detailController, int index);
    abstract protected void doRemoveDetailController(ComponentController detailController);
    abstract protected void doRemoveAllDetailControllers();
    abstract protected void doSetSelectedDetailController(ComponentController detailController);
    abstract public ComponentController getWindowedController();
    abstract public void dismiss();
}
