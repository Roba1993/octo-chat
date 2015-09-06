package de.robertschuette.octochat.chats;

import de.robertschuette.octochat.model.ChatData;
import de.robertschuette.octochat.model.ChatDataStore;
import de.robertschuette.octochat.model.ChatHandlerSettings;
import de.robertschuette.octochat.model.ChatSettings;
import de.robertschuette.octochat.util.Util;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds the main logic of this application and
 * routes the main actions.
 *
 * @author Robert Schütte
 */
public class ChatHandler extends SplitPane implements Runnable {
    private ChatDataStore cds;
    private List<Chat> chats;
    private StackPane chatArea;
    private Pane selectionArea;
    private ChatHandlerSettings chatHandlerSettings;
    private String xmlPath;

    public ChatHandler(String xmlPath) {
        this.xmlPath = xmlPath;

        // create the necessary objects
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

        // load the settings from the xml settings file
        loadChatHandlerState();

        // when no chats exist - create fb and wa chat
        if(chats.size() < 1) {
            addChat(new ChatFacebook(this, new ChatSettings("Facebook")));
            addChat(new ChatWhatsapp(this, new ChatSettings("Whats App")));
        }

        // start this thread
        new Thread(this).start();
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
        addSelectionImage(chat, 0, chats.indexOf(chat) * 50);
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

    /**
     * Run the background thread to save the settings
     * and update information
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String tmpXml = "";

        // endless loop
        while (true) {
            // sleep for 0.5s
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}

            // save the settings
            tmpXml = saveChatHandlerState(tmpXml);
        }
    }

    /**************** Private functions *************************/

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

    /**
     * Save the actual chat handler status and all the status
     * from the chats to a xml file. This function writes the
     * the new xml file only when the string has changed from
     * the input string.
     *
     * @param tmpXml the old xml file for comparison
     * @return the new tmpXml string to compare
     */
    private String saveChatHandlerState(String tmpXml) {
        try {
            // generate the document and root element
            Document doc = new Document(new Element("octa-chat"));

            // create chat handler element and add it to root
            Element eChatHandler = new Element("chat-handler");
            doc.getRootElement().addContent(eChatHandler);

            // set the information for the chat handler
            eChatHandler.addContent(new Element("notifications").setText(Boolean.toString(chatHandlerSettings.isNotifications())));

            // create chats element and add it to root
            Element eChats = new Element("chats");
            doc.getRootElement().addContent(eChats);

            // set the chat settings
            for(Chat chat : chats) {
                // create the element with the information
                Element eChat = new Element("chat");
                eChat.addContent(new Element("name").setText(chat.getChatSettings().getName()));
                eChat.addContent(new Element("notifications").setText(Boolean.toString(chat.getChatSettings().isNotifications())));

                // add the chat type attribute
                if(chat instanceof ChatFacebook) {
                    eChat.setAttribute("type", "facebook");
                }
                else if(chat instanceof ChatWhatsapp) {
                    eChat.setAttribute("type", "whats-app");
                }

                // add it to the chats element
                eChats.addContent(eChat);
            }


            // create the xml outputter
            XMLOutputter xmlOutput = new XMLOutputter();

            // get the document as string
            String tmp = xmlOutput.outputString(doc);

            // only continue when there is a change
            if(tmp.equals(tmpXml)) {
                return tmp;
            }

            // format right and write to file
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(xmlPath));

            // return string to compare the next time
            return tmp;
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

        return "";
    }

    /**
     * This function loads the old state from the xml file.
     */
    private void loadChatHandlerState() {
        try {
            // read the document
            Document doc = new SAXBuilder().build(new File(xmlPath));
            Element eRoot = doc.getRootElement();

            // get the chat handler settings
            Element eChatHandler = eRoot.getChild("chat-handler");

            // fill the chat handler settings object with data
            chatHandlerSettings.setNotifications(Boolean.parseBoolean(eChatHandler.getChildText("notifications")));

            // loop over all chats
            for(Element eChat : eRoot.getChild("chats").getChildren()) {
                // create a new chat setting
                ChatSettings chatSettings = new ChatSettings(eChat.getChildText("name"));
                chatSettings.setNotifications(Boolean.parseBoolean(eChat.getChildText("notifications")));

                // add a new facebook chat
                if("facebook".equals(eChat.getAttributeValue("type"))){
                    addChat(new ChatFacebook(this, chatSettings));
                }
                // or a new whats app chat
                else if("whats-app".equals(eChat.getAttributeValue("type"))){
                    addChat(new ChatWhatsapp(this, chatSettings));
                }
            }
        } catch (IOException | JDOMException io) {}
    }
}
