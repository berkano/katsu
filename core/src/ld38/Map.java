package ld38;

import katsu.K;
import katsu.KRoom;

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {
    @Override
    public void start() {
        super.start();
        loadFromTiledMap("test-map");
        // tiles are 32px
        // map is 16 x 16 tiles
        // camera is in centre of display
        K.graphics.camera.lookAt(32 * 8, 32 * 8, 0);
    }
}
