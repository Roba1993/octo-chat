package de.robertschuette.octochat.model;

/**
 * This class holds all settings about for
 * a chat.
 *
 * @author Robert Schütte
 */
public class ChatSettings {

    private String name;
    private boolean notifications;


    /**
     * Constructor to create a setting object.
     *
     * @param name unique name of the chat
     */
    public ChatSettings(String name) {
        this.name = name;
        notifications = true;
    }


    /******** Getter & Setter **************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
