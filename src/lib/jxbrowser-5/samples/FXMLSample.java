/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The sample demonstrates how to embed JavaFX BrowserView component into JavaFX app + FXML.
 */
public class FXMLSample extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane pane = FXMLLoader.load(FXMLSample.class.getResource("sample.fxml"));

        BorderPane browserPane = (BorderPane) pane.getCenter();
        final Browser browser = new Browser();
        browserPane.setCenter(new BrowserView(browser));

        final TextField textField = (TextField) pane.getTop();
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                browser.loadURL(textField.getText());
            }
        });

        primaryStage.setTitle("FXMLSample");
        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.show();

        browser.loadURL(textField.getText());
    }

    public static void main(String[] args) {
        Application.launch(FXMLSample.class, args);
    }
}
