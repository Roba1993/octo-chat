package de.robertschuette.octachat.util;

import de.robertschuette.octachat.chats.Chat;
import de.robertschuette.octachat.chats.ChatHandler;
import de.robertschuette.octachat.model.ChatData;
import netscape.javascript.JSObject;

/**
 * This class get injected to the javascript
 * to create a bridge from javascript to java.
 *
 * Every function in this class can be called
 * by javascript.
 *
 * @author Robert Schütte
 */
public class JsBridge {
    private Chat chat;
    private ChatHandler chatHandler;

    public JsBridge(ChatHandler chatHandler, Chat chat) {
        this.chatHandler = chatHandler;
        this.chat = chat;
    }

    public void updateStatus(JSObject json) {
        JSObject messages = (JSObject) json.getMember("messages");

        // loop over the messages and compare them with the store
        int i = 0;
        while (true) {
            // when there aren't more entries end
            if("undefined".equals(messages.getSlot(i).toString())) {
                break;
            }

            // fetch the message object from the json
            JSObject m = (JSObject) messages.getSlot(i);

            // create the chat data
            ChatData cd = new ChatData(chat, m.getMember("id").toString(), m.getMember("name").toString());
            cd.setLastMessage(m.getMember("text").toString());
            cd.setLastMessageUnread(Boolean.parseBoolean(m.getMember("unread").toString()));
            cd.setLastMessageTime(m.getMember("time").toString());
            cd.setIsOnline(Boolean.parseBoolean(m.getMember("online").toString()));

            // update the store
            chatHandler.updateChatData(cd);

            i++;
        }
    }

    /**
     * Simple println to write a string
     * to the java console.
     *
     * @param s string to print
     */
    public void println(String s) {
        System.out.println(s);
    }
}
