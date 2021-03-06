package katsu.test.troll;

import katsu.K;
import katsu.model.KEntity;
import katsu.input.KPanHandler;
import katsu.model.KRoom;
import katsu.test.troll.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollMap extends KRoom {

    private Logger logger = LoggerFactory.getLogger(TrollMap.class);
    private TrollCastleGame game;
    private TrollManager trollManager;
    private TrollGameState gameState;
    private KPanHandler panHandler = new KPanHandler();

    @Override
    public void update() {

        super.update();
        trollManager.update();
        gameState.update();

        bringEntitiesToTop(Tower.class);
        bringEntitiesToTop(Troll.class);
        bringEntitiesToTop(SwimTube.class);
    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("map");
        setupCamera();
        game = TrollCastleGame.instance();
        gameState = new TrollGameState(game, this);
        trollManager = new TrollManager(game, this);

        K.input.getMultiplexer().addProcessor(panHandler);
        K.input.getMultiplexer().addProcessor(trollManager);

        if (TrollDevFlags.randomMushroomOnStart) {
            Mushroom mushroom = new Mushroom();
            mushroom.setX(128);
            mushroom.setY(128);
            addNewEntity(mushroom);
        }

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
        if (character == ' ') {
            trollManager.handleSpaceCommand();
        }
        return false;
    }

    @Override
    public ArrayList<KEntity> findEntitiesAtPoint(int x, int y) {
        ArrayList<KEntity> result = super.findEntitiesAtPoint(x, y);
        ArrayList<KEntity> filtered = new ArrayList<>();
        for (KEntity e : result) {
            if (!(e instanceof SwimTube)) {
                filtered.add(e);
            }
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
            trollManager.entityClicked(clickedEntity);
            logger.info("Calling onClick for entity " + clickedEntity.toString());
            clickedEntity.onClick();
        }
        return false;
    }

    public void makeWaterPassable() {
        for (KEntity e : getEntities()) {
            if (e instanceof Water) {
                e.setSolid(false);
            }
        }
    }

    public void makeWaterSolid() {
        for (KEntity e : getEntities()) {
            if (e instanceof Water) {
                e.setSolid(true);
            }
        }
    }
}
