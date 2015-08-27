package de.robertschuette.octachat;

import com.teamdev.jxbrowser.chromium.Cookie;

/**
 * Cookie data class for the JxBrowser.
 *
 * @author Robert Schütte
 */
public class JxCookie implements Cookie {
    private String name;
    private String value;
    private String domain;
    private String path;
    private long creationTime;
    private long expirationTime;
    private boolean secure;
    private boolean HTTPOnly;
    private boolean session;

   /*************** Getter & Setter ******************/
    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    @Override
    public boolean isHTTPOnly() {
        return HTTPOnly;
    }

    public void setHTTPOnly(boolean HTTPOnly) {
        this.HTTPOnly = HTTPOnly;
    }

    @Override
    public boolean isSession() {
        return session;
    }

    public void setSession(boolean session) {
        this.session = session;
    }
}
