package de.robertschuette.octachat;

import java.util.Properties;

/**
 * This class improves the property class.
 *
 * @author Robert Schütte
 */
public class Settings extends Properties {


    @Override
    public synchronized Object setProperty(String key, String value) {
        // don't process null values for not existence keys
        if(value == null && !containsKey(key)) {
            return null;
        }

        // process
        return super.setProperty(key, value);
    }
}
