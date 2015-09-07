package de.robertschuette.octochat;

import de.robertschuette.octochat.chats.Chat;
import de.robertschuette.octochat.chats.ChatFacebook;
import de.robertschuette.octochat.chats.ChatHandler;
import de.robertschuette.octochat.chats.ChatWhatsapp;
import de.robertschuette.octochat.model.ChatSettings;
import de.robertschuette.octochat.util.Util;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

    private TreeItem<String> tiChats;

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
        tiChats = new TreeItem<>("Chats");

        // add the specific chat settings
        redrawChatItems();

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
                        if (chat instanceof ChatFacebook) {
                            Group group = getGroupFacebook((ChatFacebook) chat);
                            settingsArea.getChildren().add(group);
                        }

                        // when the chat is a type of whats app show wa settings group
                        else if (chat instanceof ChatWhatsapp) {
                            Group group = getGroupWhatsapp((ChatWhatsapp) chat);
                            settingsArea.getChildren().add(group);
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
            bNotification.setText(Boolean.toString(chatHandler.getChatHandlerSettings().isNotifications()));
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

        // add new facebook label
        Label lNewChatFb = new Label("New Facebook Chat");
        lNewChatFb.setLayoutX(10);
        lNewChatFb.setLayoutY(10);
        group.getChildren().add(lNewChatFb);

        // add new facebook button
        Button bNewChatFb = new Button("Create");
        bNewChatFb.setLayoutX(300);
        bNewChatFb.setLayoutY(10);
        group.getChildren().add(bNewChatFb);
        bNewChatFb.setOnAction(event -> {
            // create the new chat
            ChatSettings chatSettings = new ChatSettings(getFreeName("Facebook",0));
            chatHandler.addChat(new ChatFacebook(chatHandler, chatSettings));

            // clear the chat tree
            redrawChatItems();
        });

        // add new whats app label
        Label lNewChatWa = new Label("New Whats App Chat");
        lNewChatWa.setLayoutX(10);
        lNewChatWa.setLayoutY(40);
        group.getChildren().add(lNewChatWa);

        // add new facebook button
        Button bNewChatWa = new Button("Create");
        bNewChatWa.setLayoutX(300);
        bNewChatWa.setLayoutY(40);
        group.getChildren().add(bNewChatWa);
        bNewChatWa.setOnAction(event -> {
            // create the new chat
            ChatSettings chatSettings = new ChatSettings(getFreeName("Whats App",0));
            chatHandler.addChat(new ChatWhatsapp(chatHandler, chatSettings));

            // clear the chat tree
            tiChats.getChildren().clear();

            // add the chats to the tree
            redrawChatItems();
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
    private Group getGroupFacebook(ChatFacebook chat) {
        Group group = getGroupChatGeneral(chat);

        return group;
    }

    /**
     * This function creates a group object with the settings
     * for the whats app chat.
     *
     * @param chat for which the settings are
     * @return a group object with the settings
     */
    private Group getGroupWhatsapp(ChatWhatsapp chat) {
        Group group = getGroupChatGeneral(chat);

        return group;
    }

    private Group getGroupChatGeneral(Chat chat) {
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
        bNotification.setPrefWidth(70);
        group.getChildren().add(bNotification);
        bNotification.setOnAction(event -> {
            // change the notification setting
            chat.getChatSettings().setNotifications(!chat.getChatSettings().isNotifications());
            // change the display text
            bNotification.setText(Boolean.toString(chat.getChatSettings().isNotifications()));
            // change the click state
            bNotification.setSelected(chat.getChatSettings().isNotifications());
        });

        // label textfield name
        Label lName = new Label("Name");
        lName.setLayoutX(10);
        lName.setLayoutY(40);
        group.getChildren().add(lName);

        // warning font
        Label lNameWarning = new Label("This name already exists");
        lNameWarning.setLayoutX(180);
        lNameWarning.setLayoutY(63);
        lNameWarning.setTextFill(Color.RED);
        lNameWarning.setFont(new Font(7));
        lNameWarning.setVisible(false);
        group.getChildren().add(lNameWarning);

        // textfield name
        TextField tName = new TextField(chat.getChatSettings().getName());
        tName.setLayoutX(175);
        tName.setLayoutY(40);
        tName.setPrefWidth(200);
        group.getChildren().add(tName);
        tName.textProperty().addListener((observable, oldValue, newValue) -> {
            // remove the warning
            lNameWarning.setVisible(false);

            // exit when the newValue is the own name
            if(chat.getChatSettings().getName().equals(newValue)) {
                return;
            }

            // check if the name did not exist already
            if (existName(newValue)) {
                lNameWarning.setVisible(true);
                return;
            }

            // write the new name to the settings
            chat.getChatSettings().setName(newValue);

            // update the tree view name
            redrawChatItems();
        });

        // add remove label
        Label lRemove = new Label("Delete this chat");
        lRemove.setLayoutX(10);
        lRemove.setLayoutY(70);
        group.getChildren().add(lRemove);

        // add notification button
        Button bRemove = new Button("Remove");
        bRemove.setLayoutX(270);
        bRemove.setLayoutY(70);
        bRemove.setPrefWidth(100);
        group.getChildren().add(bRemove);
        bRemove.setOnAction(event -> {
            // delete from chat handler
            chatHandler.removeChat(chat);

            // redraw tree view
            redrawChatItems();
        });

        return group;
    }

    private boolean existName(String name) {
        // search for the name and return true if found
        for(Chat chat : chatHandler.getChats()) {
            if(chat.getChatSettings().getName().equals(name)) {
                return true;
            }
        }

        // if not found return false
        return false;
    }

    private String getFreeName(String name, int number) {
        String tmpName = name;

        // don't display the zero as number
        if(number > 0) {
            tmpName += Integer.toString(number);
        }

        // check if the name is free
        if(!existName(tmpName)) {
            return tmpName;
        }
        // if not call this method again with a higher number
        else {
            return getFreeName(name, ++number);
        }
    }

    private void redrawChatItems() {
        // clear the chat tree
        tiChats.getChildren().clear();

        // add the chats to the tree
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
    }
}
