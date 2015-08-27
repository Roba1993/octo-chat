package de.robertschuette.octachat.chats;

import de.robertschuette.octachat.util.Util;
import javafx.concurrent.Worker;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * This class enables to user to connect to irc-chats.
 *
 * @author Robert Schütte
 */
public class ChatIrc extends Region {

    private final WebView browser;
    private final WebEngine webEngine;
    private final IrcEngine ircEngine;

    /**
     * Constructor to create a new Irc Chat.
     */
    public ChatIrc() {
        // create the engine and view
        browser = new WebView();
        webEngine = browser.getEngine();

        // load the web page
        webEngine.load("file:" + Util.getResourcesPath() + "chat/chat.html");

        // bind the browser windows dimensions to the FbChat dimension
        browser.prefWidthProperty().bind(this.widthProperty());
        browser.prefHeightProperty().bind(this.heightProperty());

        // when the engine is finished with loading call this function
        webEngine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                //JsUtil.injectFile(webEngine, getClass().getClassLoader().getResource("jquery2.1.4.js").getPath());
                //JsUtil.injectFile(webEngine, getClass().getClassLoader().getResource("wa-chat.js").getPath());
                //JsUtil.injectFirebugLite(webEngine);
                System.out.println("loaded");
            }
        });

        // add the web view to the scene
        getChildren().add(browser);

        // start the irc server
        ircEngine = new IrcEngine("octa-chat", "irc.freenode.net", "#pircbotx");
    }

    /**
     * This function returns the ready state of the engine.
     * If the state is true the engine is running and usable,
     * if not, the engine has an error or is loading a new content.
     *
     * @return true when usable
     */
    public boolean isReady() {
        return webEngine.getLoadWorker().getState() == Worker.State.SUCCEEDED;
    }

    /**
     * This function adds a message to the gui element of the
     * chat to show.
     *
     * @param message to show on the gui
     * @param fromMe defines on which side the message shows up
     */
    public void displayMessage(String message, boolean fromMe) {
        if(!isReady()) {
            return;
        }

        // get the dom
        Document doc = webEngine.getDocument();

        // create a new space class
        Element eSpace = doc.createElement("div");
        eSpace.setAttribute("class", "clear");

        // create a new chat element
        Element n = doc.createElement("div");
        n.setAttribute("class", fromMe ? "from-me" : "from-them");
        Element m = doc.createElement("p");
        m.setTextContent(message);
        n.appendChild(m);

        // add the element to the head section
        doc.getElementById("chatbox").appendChild(eSpace);
        doc.getElementById("chatbox").appendChild(n);
    }


    public void testFetch() {
        webEngine.reload();
    }


    /**
     * The irc-chat listener to handle the events.
     */
    public class IrcEngine extends ListenerAdapter implements Runnable {
        private PircBotX bot;
        private String user;
        private String host;
        private String channel;

        /**
         * Creates the chat class to communicate with the irc.
         *
         * @param user your username
         * @param host the server to connect to
         * @param channel the channel you want to join
         */
        public IrcEngine(String user, String host, String channel) {
            this.user = user;
            this.host = host;
            this.channel = channel;

            new Thread(this).start();
        }

        /**
         * Starts the irc service to receive and send messages
         * to the irc.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            System.out.println("Starte IRC Backend");

            //Configure what we want our bot to do
            Configuration configuration = new Configuration.Builder()
                    .setName(user) //Set the nick of the bot
                    .setAutoNickChange(true) // when nick is used set one automatically
                    .setServerHostname(host) //Join the freenode network
                    .addAutoJoinChannel(channel) //Join the official #pircbotx channel
                    .addListener(this) //Add our listener that will be called on Events
                    .buildConfiguration();

            //Create our bot with the configuration
            bot = new PircBotX(configuration);

            //Connect to the server
            try {
                bot.startBot();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IrcException e) {
                e.printStackTrace();
            }
        }

        /**
         * This function writes a message out to the irc
         * channel
         *
         * @param message to send out
         */
        public void sendMessage(String message) {
            bot.sendIRC().message(channel, message);
        }

        /**
         * This function handles incoming messages and
         * writes them to the gui.
         *
         * @param event of an incoming message
         */
        @Override
        public void onGenericMessage(GenericMessageEvent event) {
            // write incoming messages to the gui
            displayMessage(event.getMessage(), false);

            // respond as test
            event.respond("haha genau");
        }
    }
}
