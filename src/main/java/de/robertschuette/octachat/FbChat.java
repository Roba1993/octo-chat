package de.robertschuette.octachat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * The Facebook chat window from a browser view.
 *
 * @author Robert Schütte
 */
public class FbChat extends Region {
    private final WebView browser = new WebView();
    private final WebEngine webEngine = browser.getEngine();

    /**
     * Generate the Facebook region.
     *
     */
    public FbChat() {
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
                            JsUtil.injectFile(webEngine, Util.getResourcesPath() + "jquery2.1.4.js");
                            JsUtil.injectFile(webEngine, Util.getResourcesPath() + "fbchat.js");
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


    public void testFetch() {
        if(isReady()) {
            // inject the js bridge
            JsUtil.injectBridge(webEngine);

            // test
            webEngine.executeScript("test();");
        }
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
     * Function to get the whole dom tree as String
     * ONLY FOR DEV-ANALYSE!!!
     *
     * @param doc dom to parse
     * @return dom as string
     */
    private String getStringFromDoc(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            writer.flush();
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

