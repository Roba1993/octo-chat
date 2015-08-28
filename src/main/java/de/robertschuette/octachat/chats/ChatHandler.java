package de.robertschuette.octachat.chats;

import de.robertschuette.octachat.model.ChatData;
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
    private List<Chat> chats;
    private StackPane chatArea;
    private Pane selectionArea;

    public ChatHandler() {
        chats = new ArrayList<>();

        // create the selection area
        selectionArea = new Pane();

        // create chat area
        chatArea = new StackPane();

        // add the panes
        this.getItems().addAll(selectionArea, chatArea);
        this.setDividerPositions(0.1f);
    }


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

    public void updateChatData(ChatData chatData) {

    }

    private void addSelectionImage(Chat chat, int x, int y) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(chat.getIcon()));
        } catch (FileNotFoundException e) {
            image = new Image("/img/octo.png");
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

    private void setChatsUnvisible() {
        for(Chat chat : chats) {
            chat.setVisible(false);
        }
    }
}