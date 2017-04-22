package ld38;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import mini73.Console; // TODO-POST: move to katsu
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    int lastDragX = 0;
    int lastDragY = 0;

    Logger logger = LoggerFactory.getLogger(Map.class);

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        lastDragX = screenX;
        lastDragY = screenY;

        logger.info("Detected touch down");

        Vector3 clickLocation = new Vector3(screenX, screenY, 0);
        Vector3 worldLocation = K.graphics.camera.unproject(clickLocation);

        for (KEntity e : findEntitiesAtPoint(Math.round(worldLocation.x), Math.round(worldLocation.y))) {
            logger.info("calling onClick for an instance of " + e.getClass().getSimpleName());
            e.onClick();
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        int dx = screenX - lastDragX;
        int dy = screenY - lastDragY;

        logger.info("detected drag at " + screenX + "," + screenY + " with delta " + dx + "," + dy);

        K.graphics.camera.position.x -= dx * K.graphics.camera.zoom;
        K.graphics.camera.position.y += dy * K.graphics.camera.zoom;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }
}
