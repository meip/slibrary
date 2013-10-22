package ch.hsr.slibrary.gui.controller;

import ch.hsr.slibrary.gui.form.VerticalSplitComponent;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 20:51
 * To change this template use File | Settings | File Templates.
 */
public class SplitController extends ComponentController {


    private VerticalSplitComponent splitComponent;
    private ComponentController firstController;
    private ComponentController secondController;

    public SplitController(String title, VerticalSplitComponent splitComponent) {
        super(title);
        this.component = splitComponent;
        this.splitComponent = splitComponent;
        splitComponent.getSplitPane().setDividerLocation(0.5);
    }


    public ComponentController getFirstController() {
        return firstController;
    }

    public void setFirstController(ComponentController firstController) {
        this.firstController = firstController;
        splitComponent.getSplitPane().setLeftComponent(firstController.getComponent().getContainer());
    }

    public ComponentController getSecondController() {
        return secondController;
    }

    public void setSecondController(ComponentController secondController) {

        if(secondController == null) {
            splitComponent.getSplitPane().setDividerLocation(1.0);
            splitComponent.getSplitPane().remove(splitComponent.getSplitPane().getRightComponent());
        } else {
            splitComponent.getSplitPane().setRightComponent(secondController.getComponent().getContainer());
            splitComponent.getSplitPane().setDividerLocation(0.5);
        }
        this.secondController = secondController;

    }

}
