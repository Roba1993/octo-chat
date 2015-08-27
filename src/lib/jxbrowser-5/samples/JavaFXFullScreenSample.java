/*
 * Copyright (c) 2000-2015 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.FullScreenHandler;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Demonstrates how to switch between fullscreen mode and window mode.
 */
public class JavaFXFullScreenSample extends Application {
    @Override
    public void start(Stage primaryStage) {
        Browser browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        StackPane pane = new StackPane();
        pane.getChildren().add(browserView);
        Scene scene = new Scene(pane, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        browser.setFullScreenHandler(new MyFullScreenHandler(primaryStage, pane, browserView));

        browser.loadURL("https://developer.mozilla.org/samples/domref/fullscreen.html");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class MyFullScreenHandler implements FullScreenHandler {
        private final Stage parentStage;
        private final StackPane parentRoot;
        private final BrowserView browserView;
        private Stage stage;
        private StackPane root;

        private MyFullScreenHandler(Stage parentStage, StackPane parentRoot, BrowserView browserView) {
            this.parentStage = parentStage;
            this.parentRoot = parentRoot;
            this.browserView = browserView;
            this.stage = createStage();
        }

        private Stage createStage() {
            stage = new Stage();
            root = new StackPane();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            return stage;
        }

        @Override
        public void onFullScreenEnter() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    parentRoot.getChildren().remove(browserView);
                    root.getChildren().add(browserView);
                    stage.setFullScreen(true);
                    stage.show();
                    parentStage.hide();
                }
            });
        }

        @Override
        public void onFullScreenExit() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    root.getChildren().remove(browserView);
                    parentRoot.getChildren().add(browserView);
                    stage.hide();
                    parentStage.show();
                }
            });
        }
    }
}
