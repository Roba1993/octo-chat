package de.robertschuette.octachat.util;

/**
 * This class get injected to the javascript
 * to create a bridge from javascript to java.
 *
 * Every function in this class can be called
 * by javascript.
 *
 * @author Robert Schütte
 */
public class JsBridge {

    /**
     * Simple println to write a string
     * to the java console.
     *
     * @param s string to print
     */
    public void println(String s) {
        System.out.println(s);
    }
}
