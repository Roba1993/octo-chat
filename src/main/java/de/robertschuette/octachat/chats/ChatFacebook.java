package de.robertschuette.octachat.chats;

import de.robertschuette.octachat.model.ChatData;
import de.robertschuette.octachat.model.ChatDataStore;
import de.robertschuette.octachat.util.JsUtil;
import de.robertschuette.octachat.util.Util;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.io.File;

/**
 * The Facebook chat window from a browser view.
 *
 * @author Robert Schütte
 */
public class ChatFacebook extends Chat {
    private final WebView browser;
    private final WebEngine webEngine;

    /**
     * Generate the Facebook region.
     *
     */
    public ChatFacebook() {
        // create the engine and view
        browser = new WebView();
        webEngine = browser.getEngine();

        // load the web page
        webEngine.load("https://www.messenger.com");

        // bind the browser windows dimensions to the FbChat dimension
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            //JsUtil.injectFile(webEngine, Util.getResourcesPath() + "jquery2.1.4.js");
                            //JsUtil.injectFile(webEngine, Util.getResourcesPath() + "fbchat.js");
                        }
                    }
                });

        // add the web view to the scene
        getChildren().add(browser);
    }

    /**
     * This function checks if the engine has
     * loaded to url and is ready to work with.
     *
     * @return true or false
     */
    public boolean isReady() {
        return webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED;
    }

    /**
     * Update the chat data store with the actual facebook
     * chat data.
     *
     * @param cds the data store to updat
     */
    public void update(ChatDataStore cds) {
        if(!isReady()) {
            return;
        }

        // execute the function & get the json
        JSObject json = (JSObject) webEngine.executeScript("update();");
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
            ChatData cd = new ChatData("facebook", m.getMember("id").toString(), m.getMember("name").toString());
            cd.setLastMessage(m.getMember("text").toString());
            cd.setLastMessageUnread(Boolean.parseBoolean(m.getMember("unread").toString()));
            cd.setLastMessageTime(m.getMember("time").toString());
            cd.setIsOnline(Boolean.parseBoolean(m.getMember("online").toString()));

            // update the store
            cds.updateChat(cd);

            i++;
        }
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
