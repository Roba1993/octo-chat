package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.scene.layout.Region;

import java.util.logging.Level;

/**
 * Create a new WhatsApp window to communicate with.
 *
 * @author Robert Schütte
 */
public class ChatWindowWhatsapp extends Region {
    private Browser engine;
    private BrowserView browser;


    public ChatWindowWhatsapp() {
        // disable console output
        LoggerProvider.getChromiumProcessLogger().setLevel(Level.OFF);
        LoggerProvider.getIPCLogger().setLevel(Level.OFF);
        LoggerProvider.getBrowserLogger().setLevel(Level.OFF);

        // create the browser and view
        engine = new Browser();
        browser = new BrowserView(engine);

        // bind the browser dimensions to the dimensions of this object
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        // load the website
        engine.loadURL("https://web.whatsapp.com");

        // add the browser to this window
        getChildren().add(browser);
    }
}
