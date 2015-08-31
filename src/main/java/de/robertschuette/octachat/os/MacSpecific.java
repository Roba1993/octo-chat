package de.robertschuette.octachat.os;

import com.apple.eawt.Application;
import com.aquafx_project.AquaFx;
import de.robertschuette.octachat.util.Util;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.awt.*;
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
    public void setSpecificStyle(Stage stage) {
        // style the whole application
        AquaFx.style();

        // set the mac os x dock icon
        Image image = new ImageIcon(Util.getResourcesPath()+"/img/octo.png").getImage();
        com.apple.eawt.Application.getApplication().setDockIconImage(image);

        // on window close button, only hide window
        //Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //event.consume();
                //stage.hide();
            }
        });
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
        System.out.println("Notification# "+title+" > "+message);

        // get the path to the terminal notifier app
        String path = Util.getResourcesPath()+"/mac";

        System.out.println(path);

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
            commands.add("-appIcon");
            commands.add(Util.getResourcesPath()+"img/octo.png");

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
