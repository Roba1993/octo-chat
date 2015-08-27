package de.robertschuette.octachat;

import com.sun.javafx.runtime.VersionInfo;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.os.OsSpecific;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Main class for the octachat program.
 *
 * @author Robert Schütte
 */
public class OctaChat extends Application {
    private FbChat fbChat;
    //private WaChat waChat;
    private IrcChat ircChat;
    private Group root;

    private Browser browser;
    private BrowserView browserView;

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
        fbChat = new FbChat();
        fbChat.setLayoutX(50);
        fbChat.setLayoutY(0);
        fbChat.setPrefWidth(850);
        fbChat.setPrefHeight(650);
        root.getChildren().add(fbChat);

        // define the wa chat window
        /*waChat = new WaChat();
        waChat.setLayoutX(50);
        waChat.setLayoutY(0);
        waChat.setPrefWidth(850);
        waChat.setPrefHeight(650);
        waChat.setVisible(false);
        root.getChildren().add(waChat);*/
        browser = new Browser();
        browserView = new BrowserView(browser);
        browserView.setLayoutX(50);
        browserView.setLayoutY(0);
        browserView.setPrefHeight(650);
        browserView.setPrefWidth(850);
        browserView.setVisible(false);
        root.getChildren().add(browserView);
        browser.loadURL("http://www.google.com");

        // define the irc chat window
        ircChat = new IrcChat();
        ircChat.setLayoutX(50);
        ircChat.setLayoutY(0);
        ircChat.setPrefWidth(850);
        ircChat.setPrefHeight(650);
        ircChat.setVisible(false);
        root.getChildren().add(ircChat);

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
            //waChat.setVisible(false);
            browserView.setVisible(false);
            ircChat.setVisible(false);
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
            ircChat.setVisible(false);
            fbChat.setVisible(false);
            //waChat.setVisible(true);
            browserView.setVisible(true);
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
            fbChat.setVisible(false);
            //waChat.setVisible(false);
            browserView.setVisible(false);
            ircChat.setVisible(true);
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
            //OsSpecific.getSpecific().setSpecificNotification("Robert Schuette", "Send you a new Message");
            //fbChat.testFetch();
            ircChat.displayMessage("Hello", true);
            ircChat.displayMessage("World", false);
        });
        menuItem2.setOnAction(event -> {
            ircChat.testFetch();
        });
        menu4.getItems().addAll(menuItem1, menuItem2);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);

        root.getChildren().addAll(menuBar);
    }

}