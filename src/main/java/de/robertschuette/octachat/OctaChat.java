package de.robertschuette.octachat;

import com.sun.javafx.runtime.VersionInfo;
import de.robertschuette.octachat.chats.ChatHandler;
import de.robertschuette.octachat.chats.ChatWhatsapp;
import de.robertschuette.octachat.chats.ChatFacebook;
import de.robertschuette.octachat.chats.ChatIrc;
import de.robertschuette.octachat.model.FileCookieStore;
import de.robertschuette.octachat.os.OsSpecific;
import de.robertschuette.octachat.util.Util;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Main class for the octachat program.
 *
 * @author Robert Schütte
 */
public class OctaChat extends Application {
    private Group root;

    /**
     * The main entry point for the program.
     *
     * @param args starting parameter for the program
     */
    public static void main(String args[]) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) throws Exception {
         // set a os specific style first to show dock image first
        OsSpecific.getSpecific().setSpecificStyle();

        // initialise the new cookie manager and load the cookies from the file
        FileCookieStore.init(Util.getResourcesPath() + "/cookie-store.xlm");

        // define root element for the stage
        root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        //Scene scene = new Scene(root, 500, 500, Color.WHITE);

        ChatHandler chatHandler = new ChatHandler();
        chatHandler.prefWidthProperty().bind(scene.widthProperty());
        chatHandler.prefHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(chatHandler);

        // create and add the menu
        createMenu();

        // create the stage
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.getIcons().add(new Image("/img/octo.png"));
        stage.setTitle("Octo-Chat - Java Version: " + VersionInfo.getRuntimeVersion());
        stage.show();

        chatHandler.addChat(new ChatFacebook());
        chatHandler.addChat(new ChatWhatsapp());
        //chatHandler.addChat(new ChatFacebook());

        // start the worker demon for background work
        WorkerThread wt = new WorkerThread(chatHandler);
        wt.start();
    }

    /**
     * Create the menue for the program
     *
     * @return the created menue
     */
    private void createMenu() {
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        final Menu menu4 = new Menu("Develop");
        final MenuItem menuItem1 = new MenuItem("Test");
        final MenuItem menuItem2 = new MenuItem("Test2");
        menuItem1.setOnAction(event -> {
            // insert here test code
        });
        menuItem2.setOnAction(event -> {
            // insert here test code
        });
        menu4.getItems().addAll(menuItem1, menuItem2);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        root.getChildren().addAll(menuBar);
    }

}