package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.model.ChatData;
import de.robertschuette.octachat.util.Util;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

/**
 * Create a new WhatsApp window to communicate with.
 *
 * @author Robert Schütte
 */
public class ChatWhatsapp extends Chat implements Runnable {
    private ChatHandler chatHandler;
    private Browser engine;
    private BrowserView browser;

    public ChatWhatsapp(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;

        // disable console output
        LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
        LoggerProvider.getIPCLogger().setLevel(Level.OFF);
        LoggerProvider.getBrowserLogger().setLevel(Level.OFF);

        // create the browser and view
        engine = new Browser(new BrowserContext(Util.getResourcesPath()+"/cache/jx-browser"));
        browser = new BrowserView(engine);

        // bind the browser dimensions to the dimensions of this object
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        // set this class in variable
        ChatWhatsapp chatWhatsapp = this;

        engine.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    // start thread for js lookup
                    new Thread(chatWhatsapp).start();
                }
            }
        });

        // load the website
        engine.loadURL("https://web.whatsapp.com");

        // add the browser to this window
        getChildren().add(browser);
    }

    /**
     * Run the js fetch operations to get the information
     * from the website.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        String tmpDoc = "";

        while (true) {
            // sleep for 0.5 seconds
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }

            // when nothing has changed, start loop again
            if(tmpDoc.equals(engine.getDocument().getDocumentElement().getInnerHTML())) {
                continue;
            }

            // save the new document state
            tmpDoc = engine.getDocument().getDocumentElement().getInnerHTML();

            // get a list with all chat elements
            List<DOMElement> chats = engine.getDocument().findElements(By.className("chat"));

            // loop over all elements
            for(DOMElement chat : chats) {
                // get the id
                String id = chat.getAttribute("data-reactid").split("\\$")[3];

                // get the name
                String name = chat.findElement(By.className("chat-title")).getInnerText();

                // generate a new chat data
                ChatData chatData = new ChatData(this, id, name);

                // add the last message
                chatData.setLastMessage(chat.findElement(By.className("chat-status")).findElement(By.className("emojitext")).getInnerText());

                // add unread message
                chatData.setLastMessageUnread(chat.findElement(By.className("unread-count")) != null);

                // add the chat time
                chatData.setLastMessageTime(chat.findElement(By.className("chat-time")).getInnerText());

                // apply the data to the chat handler
                chatHandler.updateChatData(chatData);
            }
        }
    }

    @Override
    public File getIcon() {
        return new File(Util.getResourcesPath()+"/img/wa-icon.png");
    }

}
