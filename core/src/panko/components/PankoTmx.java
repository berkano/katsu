package panko.components;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoTmx {

    public static ArrayList<PankoObjekt> loadEntitiesFromTmx(String tmxFile) {
        ArrayList<PankoObjekt> entities = new ArrayList<PankoObjekt>();

        TiledMap map = loadMap(tmxFile);
        PankoUI.showAlert(map.toString());

        Panko.exitWithError("loadEntitiesFromTmx not finished");

        return entities;
    }

    private static TiledMap loadMap(String name) {
        try {
            TmxMapLoader.Parameters parameters = new TmxMapLoader.Parameters();

            String resourceName = PankoResource.relativeResource("maps/" + name + ".tmx");
            return new TmxMapLoader().load(resourceName, parameters);
        } catch (Exception ex) {
            Panko.exitDueToException("Failed to load tiled map: "+name, ex);
            return null;
        }
    }


}
