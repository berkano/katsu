package ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KRoom;
import mini73.Console; // TODO-POST: move to katsu

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("test-map");
        setupCamera();
    }

    private void setupCamera() {

        int centreX = 32 * 4;
        int centreY = 32 * 4;

        K.graphics.camera.zoom = 0.25f;

        K.graphics.camera.position.x = centreX;
        K.graphics.camera.position.y = centreY;

    }

}
