package de.robertschuette.schat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

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

    public FbChat() {
        // load the web page
        webEngine.load("http://www.messenger.com");

        // bind the browser windows dimensions to the FbChat dimension
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            ScriptUtil.injectFile(webEngine, getClass().getClassLoader().getResource("jquery2.1.4.js").getPath());
                            ScriptUtil.injectFile(webEngine, getClass().getClassLoader().getResource("fbchat.js").getPath());
                            System.out.println("called");

                        }
                    }
                });

        // add the web view to the scene
        getChildren().add(browser);
    }

    public int getUnreadMessages() {
        if(webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED) {
            return (Integer) webEngine.executeScript("getUnreadMessages();");
        }
        else {
            return 0;
        }
    }


    public void testFetch() {
        Integer unread = (Integer) webEngine.executeScript("getUnreadMessages();");
        System.out.println("unread messages: "+unread);

        //System.out.println(getStringFromDoc(webEngine.getDocument()));
    }





    private String getStringFromDoc(org.w3c.dom.Document doc) {
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
