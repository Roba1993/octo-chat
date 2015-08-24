package de.robertschuette.schat;

import com.aquafx_project.AquaFx;
import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Main class for the schat program.
 *
 * @author Robert Schütte
 */
public class Schat extends Application {
    private FbChat fbChat;
    Group root = new Group();

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
        // initialise the new cookie manager and load the cookies from the file
        FileCookieStore.init(getClass().getClassLoader().getResource(".").getPath() + "/cookie-store.xlm");

        // define root element for the stage
        Scene scene = new Scene(root, 500, 500, Color.WHITE);

        // define the fb chat window
        fbChat = new FbChat();
        fbChat.setLayoutX(0);
        fbChat.setLayoutY(0);
        fbChat.setPrefWidth(900);
        fbChat.setPrefHeight(700);
        root.getChildren().add(fbChat);

        // create and add the menu
        root.getChildren().addAll(createMenu());

        // set mac style on the mac
        applyMacStyle();

        // create the stage
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle("S-Chat - Java Version: "+VersionInfo.getRuntimeVersion());
        stage.show();

        // start the worker demon for background work
        WorkerThread wt = new WorkerThread(fbChat);
        wt.start();
    }

    /**
     * Create the menue for the program
     *
     * @return the created menue
     */
    private MenuBar createMenu() {
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        final Menu menu4 = new Menu("Develop");
        final MenuItem menuItem1 = new MenuItem("Test");
        final MenuItem menuItem2 = new MenuItem("Test2");
        menuItem1.setOnAction(event -> {
            FileCookieStore.showCookies(true);
        });
        menuItem2.setOnAction(event -> {
            FileCookieStore.init(getClass().getClassLoader().getResource(".").getPath() + "/cookie-store.xlm");
            FileCookieStore.showCookies(false);
        });
        menu4.getItems().addAll(menuItem1, menuItem2);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        return menuBar;
    }

    private void applyMacStyle() {
        // only continue when we have a mac
        if(!System.getProperty("os.name").toLowerCase().contains("mac")) {
            return;
        }

        // style the whole application
        AquaFx.style();
    }
}