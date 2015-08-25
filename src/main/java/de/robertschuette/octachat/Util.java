package de.robertschuette.octachat;

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

        resourcesPath = new Util().getClass().getClassLoader().getResource("./").getPath();
        return resourcesPath;
    }

}
