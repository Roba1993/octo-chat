package de.robertschuette.octachat.util;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import javafx.scene.web.WebEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Helper class to interact with the javascript.
 *
 * @author Robert Schütte
 */
public class JsUtil {

    /**
     * Injects a local javascript file into the existing source code
     * in the WebEngine.
     *
     * @param engine the WebEngine to inject the code in
     * @param url    the path to the local file with the code
     */
    public static void injectFile(WebEngine engine, String url) {
        // try to read the source code from the file
        String sourceCode = null;
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            sourceCode = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        }

        // only continue when there is source code
        if (sourceCode == null) {
            return;
        }

        // get the dom
        Document doc = engine.getDocument();

        if (doc == null) {
            return;
        }

        // create a new script element
        Element n = doc.createElement("script");
        //n.setAttribute("type", "text/javascript");
        n.setTextContent(sourceCode);

        // add the element to the head section
        doc.getElementsByTagName("head").item(0).appendChild(n);
    }

    /**
     * Injects a local javascript file into the existing source code
     * in the WebEngine.
     *
     * @param engine the engine to inject the code in
     * @param url    the path to the local file with the code
     */
    public static void injectFile(Browser engine, String url) {
        // try to read the source code from the file
        String sourceCode = null;
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            sourceCode = sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        }

        // only continue when there is source code
        if (sourceCode == null) {
            return;
        }

        // get the dom
        DOMDocument doc = engine.getDocument();

        if (doc == null) {
            return;
        }

        // create a new script element
        DOMElement n = doc.createElement("script");
        //n.setAttribute("type", "text/javascript");
        n.setTextContent(sourceCode);

        // add the element to the head section
        doc.findElement(By.tagName("head")).appendChild(n);
    }

    /**
     * Injects a firebug analyse tool
     *
     * @param engine the WebEngine to inject the code in
     */
    public static synchronized void injectFirebugLite(WebEngine engine) {
        // get the dom
        Document doc = engine.getDocument();

        if (doc == null) {
            System.out.println("No DOC");
            return;
        }

        // create a new script element
        Element n = doc.createElement("script");
        n.setAttribute("type", "text/javascript");
        n.setAttribute("src", "https://getfirebug.com/firebug-lite.js#startOpened=true,disableWhenFirebugActive=false");

        // add the element to the head section
        doc.getElementsByTagName("head").item(0).appendChild(n);
    }

    /**
     * Injects a firebug analyse tool
     *
     * @param engine the WebEngine to inject the code in
     */
    public static synchronized void injectFirebugLite(Browser engine) {
        // get the dom
        DOMDocument doc = engine.getDocument();

        if (doc == null) {
            System.out.println("No DOC");
            return;
        }

        // create a new script element
        DOMElement n = doc.createElement("script");
        n.setAttribute("type", "text/javascript");
        n.setAttribute("src", "https://getfirebug.com/firebug-lite.js#startOpened=true,disableWhenFirebugActive=false");

        // add the element to the head section
        //doc.getElementsByTagName("head").item(0).appendChild(n);
        doc.findElement(By.tagName("head")).appendChild(n);
    }
}
