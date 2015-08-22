package de.robertschuette.schat;

import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.application.Platform;
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
    private final FbChat fbChat = new FbChat();

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
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);

        // define the fb chat window
        fbChat.setLayoutX(0);
        fbChat.setLayoutY(20);
        fbChat.setPrefWidth(900);
        fbChat.setPrefHeight(700);
        root.getChildren().add(fbChat);

        // create and add the menue
        root.getChildren().addAll(createMenue());

        // create the stage
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle(VersionInfo.getRuntimeVersion());
        stage.show();


        WorkerThread wt = new WorkerThread(fbChat);
        wt.start();
    }

    /**
     * Create the menue for the program
     *
     * @return the created menue
     */
    private MenuBar createMenue() {
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        final Menu menu4 = new Menu("Develop");
        final MenuItem menuItem1 = new MenuItem("Test");
        menuItem1.setOnAction(event -> {
            fbChat.testFetch();
        });
        menu4.getItems().addAll(menuItem1);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        return menuBar;
    }
}
