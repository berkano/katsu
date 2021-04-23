package katsu.ld48;

import katsu.K;
import katsu.input.KPanHandler;
import katsu.model.KEntity;
import katsu.model.KRoom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class LD48Map extends KRoom {

    private Logger logger = LoggerFactory.getLogger(LD48Map.class);
    private LD48Game game;
    private KPanHandler panHandler = new KPanHandler();

    @Override
    public void update() {

        super.update();

    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("map");
        setupCamera();
        game = LD48Game.instance();

        K.input.getMultiplexer().addProcessor(panHandler);

    }

    private void setupCamera() {
        int centreX = 32 * 4;
        int centreY = 32 * 4;
        K.graphics.camera.zoom = 0.25f;
        K.graphics.camera.position.x = centreX;
        K.graphics.camera.position.y = centreY;
    }

    @Override
    public boolean keyTyped(char character) {

        if (character == 'h') {
            game.ui.toggleHelp();
        }
        return false;
    }

    @Override
    public ArrayList<KEntity> findEntitiesAtPoint(int x, int y) {
        ArrayList<KEntity> result = super.findEntitiesAtPoint(x, y);
        ArrayList<KEntity> filtered = new ArrayList<>();
        for (KEntity e : result) {
//            if (!(e instanceof SwimTube)) {
//                filtered.add(e);
//            }
        }
        return filtered;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        logger.info("Detected touch up");

        if (panHandler.isDragging()) return false;

        KEntity clickedEntity = null;

        List<KEntity> entitiesUnderClick = entitiesAtScreenPoint(screenX, screenY);

        // Iterate thru entities, topmost last
        for (KEntity e : entitiesUnderClick) {
            logger.info("calling onClick for an instance of " + e.getClass().getSimpleName());
            clickedEntity = e;
        }

        if (clickedEntity != null) {
            logger.info("Calling onClick for entity " + clickedEntity.toString());
            clickedEntity.onClick();
        }
        return false;
    }

}
