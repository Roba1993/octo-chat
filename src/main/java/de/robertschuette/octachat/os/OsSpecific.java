package de.robertschuette.octachat.os;

import java.io.IOException;

/**
 * This class is the factory and interface for the operation
 * specific instructions. These are for example os specific styles
 * or notifications.
 *
 * @author Robert Schütte
 */
public abstract class OsSpecific {
    private static OsSpecific specificOs;

    /**
     * This function returns the specific object
     * for this operating system. On these you can
     * call the instructions you want.
     *
     * @return os specific object
     */
    public static OsSpecific getSpecific() {
        if(specificOs == null) {
            specificOs = chooseOs();
        }

        return specificOs;
    }

    /**
     * This function creates the os specific object
     * for each known operating system. If the requested
     * operating system is not known return a default object.
     *
     * @return os specific object
     */
    private static OsSpecific chooseOs() {
        if(System.getProperty("os.name").toLowerCase().contains("mac")) {
            return new MacSpecific();
        }
        else {
            return new DefaultSpecific();
        }
    }

    /**
     * This function generates a operation specific style
     * for the javafx gui.
     */
    public abstract void setSpecificStyle();

    /**
     * This functions creates a os specific notification
     * to inform the user about important program events.
     * For example the receiving of a new message.
     *
     * @param title of the notification
     * @param message of the notification
     */
    public abstract void setSpecificNotification(String title, String message);

    /**
     * This function displays the number of new events.
     * For example as number on the programm icon.
     *
     * @param count to show
     */
    public abstract void setSpecificEventCount(int count);
}
