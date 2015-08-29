package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.JSValue;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.model.ChatData;
import de.robertschuette.octachat.util.JsUtil;
import de.robertschuette.octachat.util.Util;

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

        // listen when the browser has finished loading
        engine.addLoadListener(new LoadAdapter() {
            @Override
            public void onFinishLoadingFrame(FinishLoadingEvent event) {
                if (event.isMainFrame()) {
                    // inject our code
                    JsUtil.injectFile(engine, Util.getResourcesPath() + "jquery2.1.4.js");
                    JsUtil.injectFile(engine, Util.getResourcesPath() + "fbchat.js");
                }
            }
        });

        // create a bridge from js to java to update the status
        engine.registerFunction("javaStatus", (JSValue... jsValues) -> {
            ChatData chatData = new ChatData(this, jsValues[0].getString(), jsValues[1].getString());
            chatData.setLastMessage(jsValues[2].getString());
            chatData.setLastMessageTime(jsValues[3].getString());
            chatData.setLastMessageUnread(jsValues[4].getBoolean());
            chatData.setIsOnline(jsValues[5].getBoolean());

            chatHandler.updateChatData(chatData);

            return null;
        });

        // load the website
        engine.loadURL("https://messenger.com");

        // add the browser to this window
        getChildren().add(browser);
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

