package ld38;

import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    private Logger logger = LoggerFactory.getLogger(Map.class);
    private TrollCastleGame game;
    TrollManager trollManager;
    GameState gameState;
    MouseHandler mouseHandler = new MouseHandler();

    boolean hasSpawnedHint = false;

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
        gameState = new GameState(game, this);
        trollManager = new TrollManager(game, this);

        K.input.getMultiplexer().addProcessor(mouseHandler);
        K.input.getMultiplexer().addProcessor(trollManager);

        if (DevHelper.randomMushroomOnStart) {
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
            handleSpaceCommand();
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

        if (hasDragged) return false;

        KEntity clickedEntity = null;

        List<KEntity> entitiesUnderClick = entitiesAtScreenPoint(screenX, screenY);

        Troll clickedTroll;

        // Iterate thru entities, topmost last
        for (KEntity e : entitiesUnderClick) {
            logger.info("calling onClick for an instance of " + e.getClass().getSimpleName());
            clickedEntity = e;
        }

        // Troll select / deselect
        if (clickedEntity instanceof Troll) {

            // After we start selecting stuff, set a reminder about clicking everything else in the map.
            if (!hasSpawnedHint) {
                hasSpawnedHint = true;
                game.task(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        Thread.sleep(30000);
                        game.ui.bottomBar.writeLine("[MAGENTA]Don't forget, you can click things to find out what they are!");
                        game.ui.bottomBar.writeLine("(Deselect any Trolls first by clicking them).");
                        return true;
                    }
                });
            }

            clickedTroll = (Troll)clickedEntity;
            logger.info("Clicked Troll " + clickedTroll.toString());
            if (clickedTroll != selectedTroll) {
                // deselect existing if we have it
                if (selectedTroll != null) {
                    selectedTroll.getAppearance().setTextureFrom(Troll.class);
                }
                logger.info("Selecting Troll " + clickedTroll.toString());
                clickedTroll.onClick();
                selectedTroll = clickedTroll;
                selectedTroll.getAppearance().setTextureFrom(SelectedTroll.class);
            } else {
                // it's already selected, so deselect it
                logger.info("Deselecting Troll " + clickedTroll.toString());
                clickedTroll.getAppearance().setTextureFrom(Troll.class);
                selectedTroll = null;
            }
        }

        if (selectedTroll != null) {
            if (clickedEntity != null) {
                if (clickedEntity != selectedTroll) {
                    selectedTroll.setTargetEntity(clickedEntity);
                    if ((clickedEntity instanceof Fish || clickedEntity instanceof Water) && !selectedTroll.hasHadPsychedelics) {
                            selectedTroll.say("[RED]Me want fish. Me scared of water. Mushroom stop me being scared.");
                    } else {
                        selectedTroll.say("i go " + clickedEntity.toString());
                    }
                }
            }
        } else {
            if (clickedEntity != null) {
                logger.info("Calling onClick for entity " + clickedEntity.toString());
                clickedEntity.onClick();
            }
        }
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
