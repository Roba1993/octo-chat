package de.robertschuette.octochat.chats;

import de.robertschuette.octochat.model.ChatData;
import de.robertschuette.octochat.model.ChatDataStore;
import de.robertschuette.octochat.model.ChatHandlerSettings;
import de.robertschuette.octochat.util.Util;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the main logic of this application and
 * routes the main actions.
 *
 * @author Robert Schütte
 */
public class ChatHandler extends SplitPane {
    private ChatDataStore cds;
    private List<Chat> chats;
    private StackPane chatArea;
    private Pane selectionArea;
    private ChatHandlerSettings chatHandlerSettings;

    public ChatHandler() {
        chats = new ArrayList<>();
        cds = new ChatDataStore(this);
        chatHandlerSettings = new ChatHandlerSettings();

        // create the selection area
        selectionArea = new Pane();

        // create chat area
        chatArea = new StackPane();

        // add the panes
        this.getItems().addAll(selectionArea, chatArea);
        this.setDividerPositions(0.1f);
    }

    /**
     * Register a new chat in this chat handler. The chat is now
     * clickable in the side and configurable over this handler.
     *
     * @param chat the chat to add
     */
    public void addChat(Chat chat) {
        // add the chat to the list
        chats.add(chat);

        // set the chat not visible if one already exist
        if(chats.size() > 1) {
            chat.setVisible(false);
        }

        // add to chat window
        chatArea.getChildren().add(chat);

        // add the image
        addSelectionImage(chat, 0, chats.indexOf(chat)*50);
    }

    /**
     * This function updates a specific chat data. When a new
     * message was found, we send automatically a new notification.
     *
     * @param chatData the chatdata
     */
    public void updateChatData(ChatData chatData) {
        cds.updateChat(chatData);
    }

    public List<Chat> getChats() {
        return chats;
    }

    public ChatHandlerSettings getChatHandlerSettings() {
        return chatHandlerSettings;
    }

    /**************** Getter & Setter *************************/

    /**
     * Internal function to add a picture for a chat
     * in the selection list.
     *
     * @param chat to add
     * @param x piston of the image
     * @param y position of the image
     */
    private void addSelectionImage(Chat chat, int x, int y) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(chat.getIcon()));
        } catch (FileNotFoundException e) {
            image = new Image(Util.getResourcesPath()+"/img/octo.png");
        }
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setOnMouseClicked(event -> {
            setChatsUnvisible();
            chat.setVisible(true);
        });

        selectionArea.getChildren().add(imageView);
    }

    /**
     * Internal function to hide all chats.
     */
    private void setChatsUnvisible() {
        for(Chat chat : chats) {
            chat.setVisible(false);
        }
    }
}
