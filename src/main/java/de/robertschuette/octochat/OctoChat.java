package de.robertschuette.octochat;

import de.robertschuette.octochat.chats.ChatHandler;
import de.robertschuette.octochat.os.OsSpecific;
import de.robertschuette.octochat.util.Util;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * Main class for the octo-chat program.
 *
 * @author Robert Schütte
 */
public class OctoChat extends Application {
    private ChatHandler chatHandler;
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
        printSymbol();

         // set a os specific style first to show dock image first
        OsSpecific.getSpecific().setSpecificStyle(stage);

        // define root element for the stage
        root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        //Scene scene = new Scene(root, 500, 500, Color.WHITE);

        chatHandler = new ChatHandler(Util.getResourcesPath()+"settings.xml");
        chatHandler.prefWidthProperty().bind(scene.widthProperty());
        chatHandler.prefHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(chatHandler);

        // create and add the menu
        createMenu();

        // create the stage
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle("Octo-Chat");
        stage.show();
    }

    /**
     * Create the menu for the program
     */
    private void createMenu() {
        final Menu menu1 = new Menu("Home");
        final MenuItem menu1Item1 = new MenuItem("Settings");
        menu1Item1.setOnAction(event -> {
            Stage stage = new Stage();
            new GuiSettings(chatHandler);
        });
        menu1.getItems().addAll(menu1Item1);

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
        menuBar.getMenus().addAll(menu1);

        root.getChildren().addAll(menuBar);
    }

    private void printSymbol() {
        System.out.println("               (////, ");
        System.out.println("          //////////////, ");
        System.out.println("         /////////////(((//,");
        System.out.println("        ((    /////(     //,/");
        System.out.println("        /   &. //// &&&  ///,     % ");
        System.out.println("        //  &&///////  ////,,    /*");
        System.out.println("        /////////(/////////**   /#*");
        System.out.println("          //////.  //////**  %///*");
        System.out.println("             ///&&%&///*  #////#/ ");
        System.out.println("           //////  /////////*// ");
        System.out.println(" ////,,/////*//////////////(//    (((( ");
        System.out.println("     #(###,,//////////(/#(((((((((((");
        System.out.println("          ////**//(*((((//  ///((");
        System.out.println("       /////***((((#/((((((/ ");
        System.out.println("        **     (((#/  #(((((((((");
        System.out.println("             ((((//      ###((((((");
        System.out.println("           (((#//           #");
        System.out.println("");
        System.out.println("         Octo-Chat Version 0.0.2");
    }

}