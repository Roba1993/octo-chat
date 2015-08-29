package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.model.ChatData;
import de.robertschuette.octachat.model.ChatDataStore;
import de.robertschuette.octachat.util.JsBridge;
import de.robertschuette.octachat.util.JsUtil;
import de.robertschuette.octachat.util.Util;
import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.File;
import java.util.logging.Level;

/**
 * The Facebook chat window from a browser view.
 *
 * @author Robert Schütte
 */
public class ChatFacebook extends Chat {
    private Browser engine;
    private BrowserView browser;
    private final ChatHandler chatHandler;

    /**
     * Generate the Facebook region.
     *
     */
    public ChatFacebook(ChatHandler chatHandler) {
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

        engine.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    System.out.println("loaded");
                    JsUtil.injectFile(engine, Util.getResourcesPath() + "jquery2.1.4.js");
                    JsUtil.injectFile(engine, Util.getResourcesPath() + "fbchat.js");
                }
            }
        });

        engine.registerFunction("javaNew", (JSValue... jsValues) -> {
            ChatData chatData = new ChatData(this, jsValues[0].getString(), jsValues[1].getString());
            chatData.setLastMessage(jsValues[2].getString());
            chatData.setLastMessageTime(jsValues[3].getString());
            chatData.setLastMessageUnread(jsValues[4].getBoolean());
            chatData.setIsOnline(jsValues[5].getBoolean());

            chatHandler.updateChatData(chatData);

            return null;
        });

        engine.registerFunction("println", (JSValue... jsValues) -> {
            System.out.println(jsValues[0].getString());
            return null;
        });

        // load the website
        engine.loadURL("https://messenger.com");

        // add the browser to this window
        getChildren().add(browser);
    }

    public void test() {
        System.out.println(engine.getDocument().getDocumentElement().getInnerHTML());
    }

    /**
     * Sets the ChatHandler which receives all updates
     * and who manages everything.
     *
     * @param chatHandler to registrate this chat
     */
    @Override
    public void setChatHandler(ChatHandler chatHandler) {

    }

    @Override
    public File getIcon() {
        return new File(Util.getResourcesPath()+"/img/fb-icon.png");
    }
}

