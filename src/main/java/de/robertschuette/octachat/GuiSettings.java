package de.robertschuette.octachat;

import de.robertschuette.octachat.chats.Chat;
import de.robertschuette.octachat.chats.ChatFacebook;
import de.robertschuette.octachat.chats.ChatHandler;
import de.robertschuette.octachat.chats.ChatWhatsapp;
import de.robertschuette.octachat.util.Util;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class creates a settings window.
 *
 * @author Robert Schütte
 */
public class GuiSettings extends Stage {
    private ChatHandler chatHandler;
    private Pane settingsArea;
    private StackPane selectionArea;

    /**
     * This Constructor creates a new Settings Window,
     * in which you can change all necessary settings
     * for a specific ChatHandler.
     *
     * @param chatHandler the ChatHandler to configure
     */
    public GuiSettings(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;

        SplitPane splitPane = new SplitPane();
        settingsArea = new Pane();
        settingsArea.getChildren().addAll(getGroupGeneral());

        selectionArea = new StackPane();

        splitPane.getItems().addAll(selectionArea, settingsArea);
        splitPane.setDividerPositions(0.3f);

        createTreeView();

        Scene scene = new Scene(splitPane, Color.WHITE);

        setScene(scene);
        setWidth(600);
        setHeight(500);
        setTitle("Settings");
        show();
    }

    /**
     * This function adds the tree view to the selection area.
     * The logic to switch between different view is insight this
     * function.
     */
    private void createTreeView() {
        // generate the main elements
        TreeItem<String> tiGeneral = new TreeItem<>("General");
        TreeItem<String> tiChats = new TreeItem<>("Chats");

        // add the specific chat settings
        for(Chat chat : chatHandler.getChats()) {
            // create a new tree item
            TreeItem<String> tiChat = new TreeItem<>(
                    // set the name
                    chat.getChatSettings().getName(),
                    // set the icon
                    getIcon(chat));

            // add the item to the chat
            tiChats.getChildren().add(tiChat);
        }

        // create the root element which is not visible
        TreeItem<String> tiRoot = new TreeItem<>("root");
        tiRoot.getChildren().addAll(tiGeneral, tiChats);

        // create the tree view and set the logic
        TreeView<String> tView = new TreeView<>(tiRoot);
        tView.setShowRoot(false);
        tView.setOnMouseClicked(event -> {
            TreeItem<String> item = tView.getSelectionModel().getSelectedItem();

            // on click of general show the general settings
            if ("General".equals(item.getValue())) {
                settingsArea.getChildren().clear();
                settingsArea.getChildren().add(getGroupGeneral());
            }

            // on click of chats show the chats settings
            else if ("Chats".equals(item.getValue())) {
                settingsArea.getChildren().clear();
                settingsArea.getChildren().add(getGroupChats());
            }

            // check if a specific chat setting was clicked
            else {
                settingsArea.getChildren().clear();

                // loop over all chats and find the correct one
                for (Chat chat : chatHandler.getChats()) {
                    if (item.getValue().equals(chat.getChatSettings().getName())) {

                        // when the chat is a type of facebook show fb settings group
                        if(chat instanceof ChatFacebook) {
                            settingsArea.getChildren().add(getGroupFacebook(chat));
                        }

                        // when the chat is a type of whats app show wa settings group
                        else if(chat instanceof ChatWhatsapp) {
                            settingsArea.getChildren().add(getGroupWhatsapp(chat));
                        }

                        // exit the loop we found the match
                        break;
                    }
                }
            }
        });

        selectionArea.getChildren().add(tView);
    }

    /**
     * Returns a ImageView object for the specific
     * chat. This can be used as icon or picture to show.
     *
     * @param chat to get the ImageView for
     * @return the ImageView object
     */
    private ImageView getIcon(Chat chat) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(chat.getIcon()));
        } catch (FileNotFoundException e) {
            image = new Image(Util.getResourcesPath()+"/img/octo.png");
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setFitHeight(15);

        return imageView;
    }

    /**
     * This function returns a group of settings objects,
     * for the general settings.
     *
     * @return group of settings
     */
    private Group getGroupGeneral() {
        Group group = new Group();

        // add notification label
        Label lNotification = new Label("Show Notifications");
        lNotification.setLayoutX(10);
        lNotification.setLayoutY(10);
        group.getChildren().add(lNotification);

        // add notification button
        ToggleButton bNotification = new ToggleButton();
        bNotification.setText(Boolean.toString(chatHandler.getChatHandlerSettings().isNotifications()));
        bNotification.setSelected(chatHandler.getChatHandlerSettings().isNotifications());
        bNotification.setLayoutX(300);
        bNotification.setLayoutY(10);
        group.getChildren().add(bNotification);
        bNotification.setOnAction(event -> {
            // change the notification setting
            chatHandler.getChatHandlerSettings().setNotifications(!chatHandler.getChatHandlerSettings().isNotifications());
            // change the display text
            bNotification.setSelected(chatHandler.getChatHandlerSettings().isNotifications());
            // change the click state
            bNotification.setSelected(chatHandler.getChatHandlerSettings().isNotifications());
        });

        return group;
    }

    /**
     * This functions returns a group object, which
     * all settings elements for the chats.
     *
     * @return group object
     */
    private Group getGroupChats() {
        Group group = new Group();

        // add notification label
        Label lNewChat = new Label("Create a new Chat");
        lNewChat.setLayoutX(10);
        lNewChat.setLayoutY(10);
        group.getChildren().add(lNewChat);

        // add notification button
        Button bNewChat = new Button("new Chat");
        bNewChat.setDisable(true);
        bNewChat.setLayoutX(300);
        bNewChat.setLayoutY(10);
        group.getChildren().add(bNewChat);
        bNewChat.setOnAction(event -> {

        });

        return group;
    }

    /**
     * This function creates a group object with the settings
     * for the facebook chat.
     *
     * @param chat for which the settings are
     * @return a group object with the settings
     */
    private Group getGroupFacebook(Chat chat) {
        Group group = new Group();

        // add notification label
        Label lNotification = new Label("Show Notifications");
        lNotification.setLayoutX(10);
        lNotification.setLayoutY(10);
        group.getChildren().add(lNotification);

        // add notification button
        ToggleButton bNotification = new ToggleButton();
        bNotification.setText(Boolean.toString(chat.getChatSettings().isNotifications()));
        bNotification.setSelected(chat.getChatSettings().isNotifications());
        bNotification.setLayoutX(300);
        bNotification.setLayoutY(10);
        group.getChildren().add(bNotification);
        bNotification.setOnAction(event -> {
            // change the notification setting
            chat.getChatSettings().setNotifications(!chat.getChatSettings().isNotifications());
            // change the display text
            bNotification.setSelected(chat.getChatSettings().isNotifications());
            // change the click state
            bNotification.setSelected(chat.getChatSettings().isNotifications());
        });

        return group;
    }

    /**
     * This function creates a group object with the settings
     * for the whats app chat.
     *
     * @param chat for which the settings are
     * @return a group object with the settings
     */
    private Group getGroupWhatsapp(Chat chat) {
        Group group = new Group();

        // add notification label
        Label lNotification = new Label("Show Notifications");
        lNotification.setLayoutX(10);
        lNotification.setLayoutY(10);
        group.getChildren().add(lNotification);

        // add notification button
        ToggleButton bNotification = new ToggleButton();
        bNotification.setText(Boolean.toString(chat.getChatSettings().isNotifications()));
        bNotification.setSelected(chat.getChatSettings().isNotifications());
        bNotification.setLayoutX(300);
        bNotification.setLayoutY(10);
        group.getChildren().add(bNotification);
        bNotification.setOnAction(event -> {
            // change the notification setting
            chat.getChatSettings().setNotifications(!chat.getChatSettings().isNotifications());
            // change the display text
            bNotification.setSelected(chat.getChatSettings().isNotifications());
            // change the click state
            bNotification.setSelected(chat.getChatSettings().isNotifications());
        });

        return group;
    }
}
