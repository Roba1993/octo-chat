package de.robertschuette.octachat.os;

import javafx.stage.Stage;

/**
 * This class executes the commands when no specific
 * operation system was found. Only java functions which
 * are universal executable on all operating systems
 * are here allowed.
 *
 * @author Robert Schütte
 */
public class DefaultSpecific extends OsSpecific {

    @Override
    public void setSpecificStyle(Stage stage) {}

    /**
     * This functions creates a os specific notification
     * to inform the user about important program events.
     * For example the receiving of a new message.
     *
     * @param title   of the notification
     * @param message of the notification
     */
    @Override
    public void setSpecificNotification(String title, String message) {
        System.out.println("Notification$> "+title+" - "+message);
    }

    /**
     * This function displays the number of new events.
     * For example as number on the programm icon.
     *
     * @param count to show
     */
    @Override
    public void setSpecificEventCount(int count) {}
}
