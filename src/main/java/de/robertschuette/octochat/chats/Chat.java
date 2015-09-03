package de.robertschuette.octochat.chats;

import de.robertschuette.octochat.model.ChatSettings;
import de.robertschuette.octochat.util.Util;
import javafx.scene.layout.Region;

import java.io.File;

/**
 * This class defines a Chat and every chat which should be
 * manageable over the chat handler must extend this class.
 *
 * @author Robert Schütte
 */
public abstract class Chat extends Region {
    /**
     * Returns a icon which indicates the chat.
     *
     * @return the icon path in a File object
     */
    public File getIcon() {
        return new File(Util.getResourcesPath()+"/img/octo.png");
    }

    /**
     * Returns the actual settings of the chat.
     *
     * @return the chat settings
     */
    abstract public ChatSettings getChatSettings();
}
