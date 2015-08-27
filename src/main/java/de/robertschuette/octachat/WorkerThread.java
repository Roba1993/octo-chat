package de.robertschuette.octachat;

import de.robertschuette.octachat.chats.ChatWindowWhatsapp;
import de.robertschuette.octachat.os.OsSpecific;
import javafx.application.Platform;

/**
 * This worker thread runs endless and checks for new income chats.
 *
 * @author Robert Schütte
 */
public class WorkerThread extends Thread {
    private FbChat fbChat;
    private ChatWindowWhatsapp chatWindowWhatsapp;
    private ChatDataStore cds;

    public WorkerThread(FbChat fbChat, ChatWindowWhatsapp chatWindowWhatsapp) {
        this.fbChat = fbChat;
        this.chatWindowWhatsapp = chatWindowWhatsapp;

        cds = new ChatDataStore();

        setDaemon(true);
    }

    /**
     * The endless daemon loop to check for unread messages.
     */
    public void run() {
        // never stop running
        while(true) {
            // use runLater to access gui components
            Platform.runLater(() -> {
                // update the data store with fb data
                fbChat.update(cds);

                // show unread messages
                OsSpecific.getSpecific().setSpecificEventCount(cds.getNumberUnread());

                // save the cookies
                FileCookieStore.save(chatWindowWhatsapp.getCookies());
            });


            // sleep for 0.5 seconds
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}
