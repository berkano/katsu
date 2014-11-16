package panko.components;

import katsu.Katsu;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoResource {

    public static String relativeResource(String resourcePath) {
        String resourceRoot = Panko.getImpl().getResourceRoot();
        return resourceRoot + "/" + resourcePath;
    }


}
