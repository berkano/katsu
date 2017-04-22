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

        int centreX = 32 * 4;
        int centreY = 32 * 4;

        K.graphics.camera.zoom = 0.25f;

        K.graphics.camera.position.x = centreX;
        K.graphics.camera.position.y = centreY;

    }
}
