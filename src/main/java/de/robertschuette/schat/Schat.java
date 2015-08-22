package de.robertschuette.schat;

import com.sun.javafx.runtime.VersionInfo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Main class for the schat program.
 *
 * @author Robert Schütte
 */
public class Schat extends Application {
    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     * <p>
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param stage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);

        FbChat fbChat = new FbChat();
        fbChat.setLayoutX(0);
        fbChat.setLayoutY(20);
        fbChat.setPrefWidth(900);
        fbChat.setPrefHeight(700);
        root.getChildren().add(fbChat);

        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        final Menu menu4 = new Menu("Develop");
        final MenuItem menuItem1 = new MenuItem("Test");
        menuItem1.setOnAction(event -> {
            fbChat.testFetch();
            System.out.println("test");
        });
        menu4.getItems().addAll(menuItem1);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        root.getChildren().addAll(menuBar);

        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle(VersionInfo.getRuntimeVersion());
        stage.show();
    }

    /**
     * The main entry point for the program.
     *
     * @param args starting parameter for the program
     */
    public static void main(String args[]) {
        launch(args);
    }
}
