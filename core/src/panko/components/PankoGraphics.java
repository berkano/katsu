package panko.components;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoGraphics {

    public static TextureRegion tileStitch(int x, int y, TiledMapTileLayer tileLayer) {

        TextureRegion result = tileLayer.getCell(x, y).getTile().getTextureRegion();
        result.flip(false, true);
        return result;

    }

}
