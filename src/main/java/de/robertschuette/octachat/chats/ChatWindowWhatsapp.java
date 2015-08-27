package de.robertschuette.octachat.chats;

import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import de.robertschuette.octachat.FileCookieStore;
import de.robertschuette.octachat.Util;
import javafx.scene.layout.Region;

import java.util.List;
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

        // set the cookies
        setCookies();

        // bind the browser dimensions to the dimensions of this object
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        // load the website
        //engine.loadURL("https://web.whatsapp.com");
        engine.loadURL("https://messenger.com");

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

    private void setCookies() {
        List<Cookie> cookies = FileCookieStore.getJxCookies();

        if(cookies == null) {
            return;
        }

        for(Cookie cookie : cookies) {
            engine.getCookieStorage().setCookie(
                    cookie.getDomain(),
                    cookie.getName(),
                    cookie.getValue(),
                    cookie.getDomain(),
                    cookie.getPath(),
                    cookie.getExpirationTime(),
                    cookie.isSecure(),
                    cookie.isHTTPOnly()
            );
        }
    }
}
