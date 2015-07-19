package katsu;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoGraphics {

    private static HashMap<Class, TextureRegion> textureCache = new HashMap<Class, TextureRegion>();

    public static TextureRegion tileStitch(int x, int y, TiledMapTileLayer tileLayer) {

        TextureRegion result = tileLayer.getCell(x, y).getTile().getTextureRegion();
        return result;

    }

    public static HashMap<Class, TextureRegion> getTextureCache() {
        return textureCache;
    }

    public static void setTextureCache(HashMap<Class, TextureRegion> textureCache) {
        PankoGraphics.textureCache = textureCache;
    }
}
