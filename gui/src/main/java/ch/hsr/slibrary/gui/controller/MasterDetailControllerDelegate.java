package ch.hsr.slibrary.gui.controller;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 21.10.13
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public interface MasterDetailControllerDelegate {

    public void willRemoveDetailController(ComponentController detailController);
    public void didRemoveDetailController(ComponentController detailController);

    public void willSelectDetailController(ComponentController detailController);
    public void didSelectDetailController(ComponentController detailController);
}
