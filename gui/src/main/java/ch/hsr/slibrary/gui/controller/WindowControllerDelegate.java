package ch.hsr.slibrary.gui.controller;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 18.10.13
 * Time: 20:50
 * To change this template use File | Settings | File Templates.
 */
public interface WindowControllerDelegate {

    public void windowDidOpenController(WindowController windowController, ComponentController controller);
    public void windowWillCloseController(WindowController windowController, ComponentController controller);
    public void windowDidCloseController(WindowController windowController, ComponentController controller);
    public void windowDidActivateController(WindowController windowController, ComponentController controller);
    public void windowDidDeactivateController(WindowController windowController, ComponentController controller);
    public void didAddWindowController(WindowController windowController, ComponentController controller);
}
