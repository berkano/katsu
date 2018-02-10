package katsu.input;

import katsu.K;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 26/04/2017.
 */
public class KPanHandler extends KInputProcessor {

    private int lastDragX = 0;
    private int lastDragY = 0;
    private boolean hasDragged = false;
    Logger logger = LoggerFactory.getLogger(KPanHandler.class);

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        hasDragged = false;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        hasDragged = true;

        int dx = screenX - lastDragX;
        int dy = screenY - lastDragY;

        logger.info("detected drag at " + screenX + "," + screenY + " with delta " + dx + "," + dy);

        K.graphics.camera.position.x -= dx * K.graphics.camera.zoom;
        K.graphics.camera.position.y += dy * K.graphics.camera.zoom;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
    }


    public boolean isDragging() {
        return hasDragged;
    }
}
