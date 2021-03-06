package de.robertschuette.octochat.model;

import de.robertschuette.octochat.chats.ChatHandler;
import de.robertschuette.octochat.os.OsSpecific;

import java.util.ArrayList;
import java.util.List;

/**
 * These store holds all chat data objects.
 *
 * @author Robert Schütte
 */
public class ChatDataStore {
    private ChatHandler chatHandler;
    private List<ChatData> chatData;

    public ChatDataStore(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;

        chatData = new ArrayList<>();
    }


    /**
     * This function receives a new chat data object and compares the
     * provider and userId with the chats in the store.
     *
     * If no match is found, the new chat get added to the store. When the
     * last message is unread the store sends a notification with the last message
     * to the operating system.
     *
     * When a match is found, the match get updated. When the last message is unread and
     * different then the old one from the store, the function sends an notification with the
     * message to the operating system
     *
     * @param newCd the new chat data
     */
    public void updateChat(ChatData newCd) {
        // loop over all chat data entries
        for(ChatData cd : chatData) {
            // check if the id and provider is the same
            if(cd.getChat().equals(newCd.getChat()) &&
                    cd.getUserId().equals(newCd.getUserId())) {

                // check if the new message flag is set new
                if(newCd.isLastMessageUnread() && !cd.isLastMessageUnread()) {
                    // check if notifications are enabled
                    if(chatHandler.getChatHandlerSettings().isNotifications() &&
                            cd.getChat().getChatSettings().isNotifications()) {
                        // send the notification
                        OsSpecific.getSpecific().setSpecificNotification(newCd.getUserName(), newCd.getLastMessage());
                    }
                }

                // update the values
                cd.setUserName(newCd.getUserName() != null ? newCd.getUserName() : cd.getUserName());
                cd.setLastMessage(newCd.getLastMessage() != null ? newCd.getLastMessage() : cd.getLastMessage());
                cd.setLastMessageTime(newCd.getLastMessageTime() != null ? newCd.getLastMessageTime() : cd.getLastMessageTime());
                cd.setLastMessageUnread(newCd.isLastMessageUnread());
                cd.setIsOnline(newCd.isOnline());

                // end this function
                return;
            }
        }

        // when we reach this section the chat isn't in the store
        // add the chat
        chatData.add(newCd);

        // when the message is unread, send notification
        if(newCd.isLastMessageUnread()) {
            // check if notifications are enabled
            if(chatHandler.getChatHandlerSettings().isNotifications() &&
                    newCd.getChat().getChatSettings().isNotifications()) {
                OsSpecific.getSpecific().setSpecificNotification(newCd.getUserName(), newCd.getLastMessage());
            }
        }
    }

    /**
     * This function adds a new chat data to the store
     *
     * @param cd the new chat data
     */
    public void addChat(ChatData cd) {
        chatData.add(cd);
    }

    /**
     * Returns the number of unread messages.
     *
     * @return number of unread messages
     */
    public int getNumberUnread() {
        int i=0;

        // loop over the store and count
        for(ChatData cd : chatData) {
            if(cd.isLastMessageUnread()) {
                i++;
            }
        }

        return i;
    }
}
