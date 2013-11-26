package ch.hsr.slibrary.gui.util;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: dominik
 * Date: 26.11.13
 * Time: 09:46
 * To change this template use File | Settings | File Templates.
 */
public class NotificationCenter {

    private static NotificationCenter instance;
    private Map<String, Set<NotificationResponder>> responderMap = new HashMap<>();

    public static NotificationCenter getInstance() {
        if(instance == null) instance = new NotificationCenter();
        return instance;
    }

    public void addResponder(String messageID, NotificationResponder responder) {
        Set<NotificationResponder> responderSet = responderMap.get(messageID);
        if(responderSet == null) responderSet = new HashSet<>();
        responderSet.add(responder);
        responderMap.put(messageID, responderSet);
    }

    public void removeResponder(String messageID, NotificationResponder responder) {
        Set<NotificationResponder> responderSet = responderMap.get(messageID);
        if(responderSet == null) {
            responderSet.remove(responder);
        }
    }

    public void removeResponder(NotificationResponder responder) {
        for(Map.Entry<String, Set<NotificationResponder>> entry: responderMap.entrySet()) {
            if(entry.getValue() != null) entry.getValue().remove(responder);
        }
    }

    public void sendNotification(String messageID, Object messageBody) {
        Set<NotificationResponder> responderSet = responderMap.get(messageID);
        for(NotificationResponder responder : responderSet) {
            responder.receiveNotification(new Notification(messageID, messageBody));
        }
    }

    public void sendNotification(String messageID) {
        sendNotification(messageID, null);
    }
}
