package ld38;

import katsu.KRoom;

/**
 * Created by shaun on 21/04/2017.
 */
public class LD38BaseRoom extends KRoom {
    @Override
    public void start() {
        super.start();
        loadFromTiledMap("test-map");
    }
}
