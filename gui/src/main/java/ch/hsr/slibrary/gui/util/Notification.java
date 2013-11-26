package ch.hsr.slibrary.gui.util;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 26.11.13
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */
public class Notification {
    public String messageID;
    public Object messageBody;

    public Notification(String messageID, Object messageBody) {
        this.messageBody = messageBody;
        this.messageID = messageID;
    }
}
