package de.robertschuette.octachat.chats;

import de.robertschuette.octachat.util.Util;
import javafx.scene.layout.Region;

import java.io.File;

/**
 * Created by roba on 27.08.15.
 */
public abstract class Chat extends Region {

    /**
     * Sets the ChatHandler which receives all updates
     * and who manages everything.
     *
     * @param chatHandler to registrate this chat
     */
    abstract public void setChatHandler(ChatHandler chatHandler);

    /**
     * Returns a icon which indicates the chat.
     *
     * @return the icon path in a File object.
     */
    public File getIcon() {
        return new File(Util.getResourcesPath()+"/img/octo.png");
    }
}
