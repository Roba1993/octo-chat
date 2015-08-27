package de.robertschuette.octachat;

import com.sun.javafx.runtime.VersionInfo;
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
    private ChatFacebook chatFacebook;
    private ChatWhatsapp chatWhatsapp;
    private ChatIrc chatIrc;
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

        // define the fb chat window
        chatFacebook = new ChatFacebook();
        chatFacebook.setLayoutX(50);
        chatFacebook.setLayoutY(0);
        chatFacebook.setPrefWidth(850);
        chatFacebook.setPrefHeight(650);
        root.getChildren().add(chatFacebook);

        // define the whats app chat window
        chatWhatsapp = new ChatWhatsapp();
        chatWhatsapp.setLayoutX(50);
        chatWhatsapp.setLayoutY(0);
        chatWhatsapp.setPrefHeight(650);
        chatWhatsapp.setPrefWidth(850);
        chatWhatsapp.setVisible(false);
        root.getChildren().add(chatWhatsapp);

        // define the irc chat window
        chatIrc = new ChatIrc();
        chatIrc.setLayoutX(50);
        chatIrc.setLayoutY(0);
        chatIrc.setPrefWidth(850);
        chatIrc.setPrefHeight(650);
        chatIrc.setVisible(false);
        root.getChildren().add(chatIrc);

        // show the chat images
        Image fbImage = new Image("/img/fb-icon.png");
        ImageView fbImageViewer = new ImageView();
        fbImageViewer.setImage(fbImage);
        fbImageViewer.setFitWidth(50);
        fbImageViewer.setLayoutX(0);
        fbImageViewer.setLayoutY(0);
        fbImageViewer.setPreserveRatio(true);
        fbImageViewer.setSmooth(true);
        fbImageViewer.setCache(true);
        fbImageViewer.setOnMouseClicked(event -> {
            chatFacebook.setVisible(true);
            chatWhatsapp.setVisible(false);
            chatIrc.setVisible(false);
        });
        root.getChildren().add(fbImageViewer);

        Image waImage = new Image("/img/wa-icon.png");
        ImageView waImageViewer = new ImageView();
        waImageViewer.setImage(waImage);
        waImageViewer.setFitWidth(50);
        waImageViewer.setLayoutX(0);
        waImageViewer.setLayoutY(50);
        waImageViewer.setPreserveRatio(true);
        waImageViewer.setSmooth(true);
        waImageViewer.setCache(true);
        waImageViewer.setOnMouseClicked(event -> {
            chatIrc.setVisible(false);
            chatFacebook.setVisible(false);
            chatWhatsapp.setVisible(true);
        });
        root.getChildren().add(waImageViewer);

        Image ircImage = new Image("/img/irc-icon.png");
        ImageView ircImageViewer = new ImageView();
        ircImageViewer.setImage(ircImage);
        ircImageViewer.setFitWidth(50);
        ircImageViewer.setLayoutX(0);
        ircImageViewer.setLayoutY(100);
        ircImageViewer.setPreserveRatio(true);
        ircImageViewer.setSmooth(true);
        ircImageViewer.setCache(true);
        ircImageViewer.setOnMouseClicked(event -> {
            chatFacebook.setVisible(false);
            chatWhatsapp.setVisible(false);
            chatIrc.setVisible(true);
        });
        root.getChildren().add(ircImageViewer);

        // create and add the menu
        createMenu();

        // create the stage
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.getIcons().add(new Image("/img/octo.png"));
        stage.setTitle("Octo-Chat - Java Version: " + VersionInfo.getRuntimeVersion());
        stage.show();

        // start the worker demon for background work
        WorkerThread wt = new WorkerThread(chatFacebook, chatWhatsapp);
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
            //OsSpecific.getSpecific().setSpecificNotification("Robert Schuette", "Send you a new Message");
            //fbChat.testFetch();
            chatIrc.displayMessage("Hello", true);
            chatIrc.displayMessage("World", false);
        });
        menuItem2.setOnAction(event -> {
            chatIrc.testFetch();
        });
        menu4.getItems().addAll(menuItem1, menuItem2);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        root.getChildren().addAll(menuBar);
    }

}