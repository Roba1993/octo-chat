package de.robertschuette.octachat;

import com.aquafx_project.AquaFx;
import com.sun.javafx.runtime.VersionInfo;
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
    private FbChat fbChat;
    private WaChat waChat;
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
        // initialise the new cookie manager and load the cookies from the file
        FileCookieStore.init(getClass().getClassLoader().getResource(".").getPath() + "/cookie-store.xlm");

        // define root element for the stage
        root = new Group();

        // define the fb chat window
        fbChat = new FbChat();
        fbChat.setLayoutX(50);
        fbChat.setLayoutY(0);
        fbChat.setPrefWidth(850);
        fbChat.setPrefHeight(650);
        root.getChildren().add(fbChat);

        // define the wa chat window
        waChat = new WaChat();
        waChat.setLayoutX(50);
        waChat.setLayoutY(0);
        waChat.setPrefWidth(850);
        waChat.setPrefHeight(650);
        waChat.setVisible(false);
        root.getChildren().add(waChat);

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
            fbChat.setVisible(true);
            waChat.setVisible(false);
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
            fbChat.setVisible(false);
            waChat.setVisible(true);
        });
        root.getChildren().add(waImageViewer);

        // create and add the menu
        createMenu();

        // set mac style on the mac
        applyMacStyle();

        // create the stage
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle("S-Chat - Java Version: " + VersionInfo.getRuntimeVersion());
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
    private void createMenu() {
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");
        final Menu menu3 = new Menu("Help");
        final Menu menu4 = new Menu("Develop");
        final MenuItem menuItem1 = new MenuItem("Test");
        final MenuItem menuItem2 = new MenuItem("Test2");
        menuItem1.setOnAction(event -> {
            waChat.testFetch();
        });
        menuItem2.setOnAction(event -> {
            FileCookieStore.showCookies();
        });
        menu4.getItems().addAll(menuItem1, menuItem2);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        root.getChildren().addAll(menuBar);
    }

    /**
     * Style the whole application in a os x design when
     * this actual machine is a mac
     */
    private void applyMacStyle() {
        // only continue when we have a mac
        if(!System.getProperty("os.name").toLowerCase().contains("mac")) {
            return;
        }

        // style the whole application
        AquaFx.style();
    }
}