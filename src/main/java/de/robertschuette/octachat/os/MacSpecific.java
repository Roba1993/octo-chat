package de.robertschuette.octachat.os;

import com.apple.eawt.Application;
import com.aquafx_project.AquaFx;
import de.robertschuette.octachat.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for Mac OS X specific operations.
 */
public class MacSpecific extends OsSpecific {

    /**
     * This functions styles the whole gui
     * in a mac os x specific view and set
     * the menu bar in the top menu.
     */
    @Override
    public void setSpecificStyle() {
        // style the whole application
        AquaFx.style();
    }

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
        // get the path to the terminal notifier app
        String path = Util.getResourcesPath()+"mac";

        // make the app executable
        new File(path + "/terminal-notifier.app").setExecutable(true);
        new File(path + "/terminal-notifier.app/Contents/MacOS/terminal-notifier").setExecutable(true);

        // send the notification
        try {
            //Build command
            List<String> commands = new ArrayList<>();
            commands.add("./terminal-notifier.app/Contents/MacOS/terminal-notifier");

            //Add arguments
            commands.add("-title");
            commands.add(title);
            commands.add("-message");
            commands.add(message);

            //Run macro on target
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.directory(new File(path));
            pb.redirectErrorStream(true);
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function displays the number of new events.
     * For example as number on the programm icon.
     *
     * @param count to show
     */
    @Override
    public void setSpecificEventCount(int count) {
        if(count == 0) {
            Application.getApplication().setDockIconBadge(null);
        }
        else {
            Application.getApplication().setDockIconBadge(Integer.toString(count));
        }
    }
}
