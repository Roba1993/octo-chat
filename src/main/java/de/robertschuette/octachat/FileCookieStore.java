package de.robertschuette.octachat;


import com.teamdev.jxbrowser.chromium.Cookie;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom cookie storage for the application. This
 * will store all the cookies to a file so that it persists
 * across application restarts.
 *
 *
 * @author Robert Schütte
 */
public class FileCookieStore {
    private static String path;
    private static CookieManager manager;
    private static String lastDoc;
    private static List<Cookie> jxCookies;

    /**
     * Create a new cookie manager to get access to the cookie store.
     * When a file is found at the specified path, this function is
     * trying to load cookies from it.
     *
     * @param path to load cookies from
     */
    public static void init(String path) {
        // set the path
        FileCookieStore.path = path;

        // generate a new cookie manager
        manager = new CookieManager();

        try {
            // open the xml file
            Document document = new SAXBuilder().build(new File(path));
            Element rootNode = document.getRootElement();

            // save the xml string to compare for changes in the save() function
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            lastDoc = xmlOutput.outputString(document);

            // get the fx cookie elements
            Element fxStore = rootNode.getChild("fx-store");
            List fxList = fxStore.getChildren("cookie");

            // loop over all cookies
            for (Object aList : fxList) {
                Element node = (Element) aList;

                // load the uri
                URI uri = URI.create(node.getChildText("uri"));

                // load the cookie
                HttpCookie cookie = generateHttpCookie(node);

                // add the cookie and uri to the cookie store
                manager.getCookieStore().add(uri, cookie);
            }

            // generate the jxCookie list
            jxCookies = new ArrayList<>();

            // get the jx cookie elements
            Element jxStore = rootNode.getChild("jx-store");
            List jxList = jxStore.getChildren("cookie");

            // loop over all cookies
            for (Object aList : jxList) {
                Element node = (Element) aList;

                // get the cookies and add them to the list
                jxCookies.add(generateJxCookie(node));
            }

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException e) {
            System.out.println(e.getMessage());
        }

        // set fx cookie manager as default
        CookieHandler.setDefault(manager);
    }

    /**
     * This function saves all cookies the the .xml file
     * which is defined over the init(path) function.
     */
    public static void save(List<Cookie> jxCookies) {
        try {
            // create the root element
            Document doc = new Document(new Element("cookie-store"));

            // generate the fx cookie store
            Element fxStore = new Element("fx-store");

            // iterate over all cookies from the Javafx WebEngine
            for(HttpCookie httpCookie : manager.getCookieStore().getCookies()) {
                // create the cookie element and add it
                fxStore.addContent(generateXmlCookie(httpCookie));
            }

            // add the fx cookie store to the root
            doc.getRootElement().addContent(fxStore);

            // generate the jx cookie store
            Element jxStore = new Element("jx-store");

            // iterate over all cookies from the JxBrowser
            for(Cookie cookie : jxCookies) {
                // create a cookie element and add it
                jxStore.addContent(generateXmlCookie(cookie));
            }

            // add the jx cookie store to the root
            doc.getRootElement().addContent(jxStore);

            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice
            xmlOutput.setFormat(Format.getPrettyFormat());

            // compare for changes
            String newStringDoc = xmlOutput.outputString(doc);
            if(newStringDoc.equals(lastDoc)) {
                return;
            }

            // set the new string for later comparison
            lastDoc = newStringDoc;

            // write the dom to the file
            xmlOutput.output(doc, new FileWriter(path));
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

    }

    /**
     * Get the loaded JxCookies for the JxBrowser.
     *
     * @return the loaded JxCookies
     */
    public static List<Cookie> getJxCookies() {
        return jxCookies;
    }

    /**
     * Converts a HttpCookie into a xml element with
     * all available information.
     *
     * @param httpCookie to get the information from
     * @return the xml element
     */
    private static Element generateXmlCookie(HttpCookie httpCookie) {
        // create the cookie element
        Element cookie = new Element("cookie");
        cookie.setAttribute("engine", "fx-engine");

        // add the attributes
        cookie.addContent(new Element("name-value").setText(httpCookie.toString()));
        cookie.addContent(new Element("uri").setText(createUri(httpCookie.getDomain()).toASCIIString()));
        cookie.addContent(new Element("domain").setText(httpCookie.getDomain()));
        cookie.addContent(new Element("path").setText(httpCookie.getPath()));
        cookie.addContent(new Element("secure").setText(Boolean.toString(httpCookie.getSecure())));
        cookie.addContent(new Element("maxage").setText(Long.toString(httpCookie.getMaxAge())));
        cookie.addContent(new Element("portlist").setText(httpCookie.getPortlist()));
        cookie.addContent(new Element("comment").setText(httpCookie.getComment()));
        cookie.addContent(new Element("commenturl").setText(httpCookie.getCommentURL()));
        cookie.addContent(new Element("discard").setText(Boolean.toString(httpCookie.getDiscard())));
        cookie.addContent(new Element("version").setText(Integer.toString(httpCookie.getVersion())));

        return cookie;
    }

    /**
     * Generates a new HttpCookie from a xml element.
     *
     * @param element which holds the cookie information
     * @return a new HttpCookie
     */
    private static HttpCookie generateHttpCookie(Element element) {
        // generate the new cookie
        HttpCookie cookie = HttpCookie.parse(element.getChildText("name-value")).get(0);

        // set the values
        cookie.setDomain(element.getChildText("domain"));
        cookie.setPath(element.getChildText("path"));
        cookie.setSecure(Boolean.parseBoolean(element.getChildText("secure")));
        cookie.setMaxAge(Long.parseLong(element.getChildText("maxage")));
        cookie.setPortlist(element.getChildText("portlist"));
        cookie.setComment(element.getChildText("comment"));
        cookie.setCommentURL(element.getChildText("commenturl"));
        cookie.setDiscard(Boolean.parseBoolean(element.getChildText("discard")));
        cookie.setVersion(Integer.parseInt(element.getChildText("version")));

        return cookie;
    }

    /**
     * Generates a xml element with the information from the cookie.
     *
     * @param jxCookie to get the informatin from
     * @return a new xml element
     */
    private static Element generateXmlCookie(Cookie jxCookie) {
        // create the cookie element
        Element cookie = new Element("cookie");
        cookie.setAttribute("engine", "jx-engine");

        // add the attributes
        cookie.addContent(new Element("name").setText(jxCookie.getName()));
        cookie.addContent(new Element("value").setText(jxCookie.getValue()));
        cookie.addContent(new Element("domain").setText(jxCookie.getDomain()));
        cookie.addContent(new Element("path").setText(jxCookie.getPath()));
        cookie.addContent(new Element("maxage").setText(Long.toString(jxCookie.getExpirationTime())));
        cookie.addContent(new Element("create-time").setText(Long.toString(jxCookie.getCreationTime())));
        cookie.addContent(new Element("secure").setText(Boolean.toString(jxCookie.isSecure())));
        cookie.addContent(new Element("http-only").setText(Boolean.toString(jxCookie.isHTTPOnly())));
        cookie.addContent(new Element("session").setText(Boolean.toString(jxCookie.isSession())));

        return cookie;
    }

    /**
     * Generates a new JxCookie from a xml element.
     *
     * @param element to get the information from
     * @return a new JxCookie
     */
    private static Cookie generateJxCookie(Element element) {
        JxCookie cookie = new JxCookie();

        // load the cookie
        cookie.setName(element.getChildText("name"));
        cookie.setValue(element.getChildText("value"));
        cookie.setDomain(element.getChildText("domain"));
        cookie.setPath(element.getChildText("path"));
        cookie.setExpirationTime(Long.parseLong(element.getChildText("maxage")));
        cookie.setCreationTime(Long.parseLong(element.getChildText("create-time")));
        cookie.setSecure(Boolean.parseBoolean(element.getChildText("secure")));
        cookie.setHTTPOnly(Boolean.parseBoolean(element.getChildText("http-only")));
        cookie.setSession(Boolean.parseBoolean(element.getChildText("session")));

        return cookie;
    }

    /**
     * This function create a URI from a domain.
     *
     * @param s the domain string
     * @return uri of the given string
     */
    private static URI createUri(String s) {
        // every uri have to start with www
        if(s.startsWith(".")) {
            s = "www" + s;
        }

        try {
            return new URI(s);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    public static void showCookies() {
        // iterate over all cookies and save them to the dom
        for(HttpCookie httpCookie : manager.getCookieStore().getCookies()) {
            System.out.println(createUri(httpCookie.getDomain()).toASCIIString()+" # "+
                    httpCookie.toString()+" - "+httpCookie.getSecure()+" - "+httpCookie.getPath());
        }
    }

}