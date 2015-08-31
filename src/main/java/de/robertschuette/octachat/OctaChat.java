package de.robertschuette.octachat;

import de.robertschuette.octachat.chats.ChatFacebook;
import de.robertschuette.octachat.chats.ChatHandler;
import de.robertschuette.octachat.chats.ChatWhatsapp;
import de.robertschuette.octachat.os.OsSpecific;
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
        printSymbol();

         // set a os specific style first to show dock image first
        OsSpecific.getSpecific().setSpecificStyle(stage);

        // define root element for the stage
        root = new Group();
        Scene scene = new Scene(root, Color.WHITE);
        //Scene scene = new Scene(root, 500, 500, Color.WHITE);

        ChatHandler chatHandler = new ChatHandler();
        chatHandler.prefWidthProperty().bind(scene.widthProperty());
        chatHandler.prefHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(chatHandler);

        // create and add the menu
        //createMenu();

        // create the stage
        stage.setScene(scene);
        stage.setWidth(900);
        stage.setHeight(700);
        stage.setTitle("Octo-Chat");
        stage.show();

        chatHandler.addChat(new ChatFacebook(chatHandler));
        chatHandler.addChat(new ChatWhatsapp(chatHandler));
    }

    /**
     * Create the menu for the program
     */
    private void createMenu() {
        //final Menu menu1 = new Menu("File");
        //final Menu menu2 = new Menu("Options");
        //final Menu menu3 = new Menu("Help");
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
        menuBar.getMenus().addAll(menu4);

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
        System.out.println("             ((((//      ###(((((( ");
        System.out.println("           (((#//           #");
        System.out.println("");
        System.out.println("         Octo-Chat Version 0.0.1");
    }

}