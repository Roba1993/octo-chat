package de.robertschuette.octachat.model;

/**
 * This object stores all information about a
 * specific chat.
 *
 * @author Robert Schütte
 */
public class ChatData {
    private String providerName;
    private String userId;
    private String userName;
    private String lastMessage;
    private String lastMessageTime;
    private boolean lastMessageUnread;
    private boolean isOnline;

    /**
     * Constructor to create a new chat data object.
     *
     * @param providerName whats app / facebook
     * @param userId id of the user
     * @param userName name of the user
     */
    public ChatData(String providerName, String userId, String userName) {
        this.providerName = providerName;
        this.userId = userId;
        this.userName = userName;

        lastMessage = "";
        lastMessageTime = "";
    }

    @Override
    public String toString() {
        return providerName+"> "+userId+" # "+userName+" : "+lastMessage+" - "+lastMessageTime
                +" "+(lastMessageUnread ? "new" : "old")+" "+(isOnline ? "on" : "off") ;
    }

    /*************** Getter & Setter *****************/
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public boolean isLastMessageUnread() {
        return lastMessageUnread;
    }

    public void setLastMessageUnread(boolean lastMessageUnread) {
        this.lastMessageUnread = lastMessageUnread;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }
}
