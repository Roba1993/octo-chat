package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserContext;
import com.teamdev.jxbrowser.chromium.Cookie;
import com.teamdev.jxbrowser.chromium.LoggerProvider;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.util.Util;
import javafx.scene.layout.Region;

import java.util.List;
import java.util.logging.Level;

/**
 * Create a new WhatsApp window to communicate with.
 *
 * @author Robert Schütte
 */
public class ChatWhatsapp extends Region {
    private Browser engine;
    private BrowserView browser;


    public ChatWhatsapp() {
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

        // load the website
        engine.loadURL("https://web.whatsapp.com");
        //engine.loadURL("https://messenger.com");

        // add the browser to this window
        getChildren().add(browser);
    }

    /**
     * Return all Cookies as List.
     *
     * @return all cookies
     */
    public List<Cookie> getCookies() {
        return engine.getCookieStorage().getAllCookies();
    }

}
