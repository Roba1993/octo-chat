package de.robertschuette.octochat.util;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.security.CodeSource;

/**
 * In this class you find function for the general
 * use all over the application.
 *
 * @author Robert Schütte
 */
public class Util {
    private static String resourcesPath;

    /**
     * This function returns the absolute path to the
     * resources directory as String.
     *
     * @return resources dir path
     */
    public static String getResourcesPath() {
        if(resourcesPath != null) {
            return  resourcesPath;
        }

        String jarDir = ".";

        // get the path of the .jar
        try {
            CodeSource codeSource = Util.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            jarDir = jarFile.getParentFile().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File f = new File(jarDir+"/data");
        if(f.exists() && f.isDirectory()) {
            resourcesPath = jarDir+"/data/";
        }
        else {
            resourcesPath = jarDir+"/";
        }

        return resourcesPath;
    }


    /**
     * Function to get the whole dom tree as String
     * ONLY FOR DEV-ANALYSE!!!
     *
     * @param doc dom to parse
     * @return dom as string
     */
    public static String getStringFromDoc(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            writer.flush();
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
