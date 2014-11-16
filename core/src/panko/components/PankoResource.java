package panko.components;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoResource {

    public static String relativeResource(String resourcePath) {
        String resourceRoot = Panko.getImplementation().getResourceRoot();
        return resourceRoot + "/" + resourcePath;
    }


}
