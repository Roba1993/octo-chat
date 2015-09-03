package de.robertschuette.octachat.model;

/**
 * This class holds all settings about for
 * a chat handler.
 *
 * @author Robert Schütte
 */
public class ChatHandlerSettings {

    private boolean notifications;


    public ChatHandlerSettings() {
        notifications = true;
    }

    /************** Getter & Setter ************/
    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
