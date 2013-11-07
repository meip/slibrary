package ch.hsr.slibrary.gui.controller.system;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 04.11.13
 * Time: 13:16
 * To change this template use File | Settings | File Templates.
 */
public interface TabControllerDelegate {
    void tabControllerDidAddController(TabController tabController, ComponentController controller);
    void tabControllerDidRemoveController(TabController tabController, ComponentController controller);
    void tabControllerDidSelectController(TabController tabController, ComponentController controller);
}
