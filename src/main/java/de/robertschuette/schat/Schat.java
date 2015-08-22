package de.robertschuette.schat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Created by roba on 22.08.15.
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
        Button btn = new Button(">> Click <<");
        btn.setOnAction(e -> System.out.println("Hello JavaFX 8"));
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        stage.setScene(new Scene(root));
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setTitle("JavaFX 8 app");
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
