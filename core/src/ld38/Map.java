package ld38;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Select;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import ld38.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    private int lastDragX = 0;
    private int lastDragY = 0;
    private boolean hasDragged = false;

    private Logger logger = LoggerFactory.getLogger(Map.class);

    private TrollCastleGame game;

    private Troll selectedTroll = null;

    @Override
    public void update() {
        super.update();

        // Handle trolls dying
        if (selectedTroll != null) {
            if (selectedTroll.isDestroyed()) {
                selectedTroll = null;
            }
        }

        game.trolls = 0;
        List<KEntity> trolls = new ArrayList<>();
        List<KEntity> towers = new ArrayList<>();
        for (KEntity e: getEntities()) {
            if (e instanceof Troll) {
                Troll t = (Troll) e;
                trolls.add(t);
                game.trolls++;
            }
            if (e instanceof BaseTower) {
                towers.add(e);
            }
        }

        // z order - towers
        for (KEntity e: towers) {
            getEntities().remove(e);
            getEntities().add(e);
        }

        // z order - trolls
        for (KEntity e: trolls) {
            getEntities().remove(e);
            getEntities().add(e);
        }

    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("map");
        setupCamera();
        game = TrollCastleGame.instance();
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

        hasDragged = false;

        lastDragX = screenX;
        lastDragY = screenY;

        return false;
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

    private void handleBuildCommand() {
        // do we have a selected troll?
        if (selectedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            return;
        }

        // work out what we're on top of, first.
        List<KEntity> under = findEntitiesAtPoint(selectedTroll.getX() + 2, selectedTroll.getY() + 2);


        boolean foundSand = false;
        boolean foundWall = false;
        boolean foundTower = false;
        boolean foundGoldTower = false;

        for (KEntity e : under) {
            if (e instanceof Sand) foundSand = true;
            if (e instanceof Wall) foundWall = true;
            if (e instanceof Tower) foundTower = true;
            if (e instanceof GoldTower) foundGoldTower = true;
        }

        if (!foundSand) {
            // we are not in a suitable place for building
            selectedTroll.say("ogg Sand digg mog.");
            return;
        }

        if (!foundWall) {
            if (game.stone < 10) {
                selectedTroll.say("nog 10 Stone gott.");
                return;
            }
            game.stone -= 10;
            Wall wall = new Wall();
            wall.setX(selectedTroll.getX());
            wall.setY(selectedTroll.getY());
            addNewEntity(wall);
            game.build.play();
            return;
        }

        if (!foundTower) {
            if (game.stone < 20) {
                selectedTroll.say("nog 20 Stone gott.");
                return;
            }
            game.stone -= 20;
            Tower tower = new Tower();
            tower.setX(selectedTroll.getX());
            tower.setY(selectedTroll.getY());
            addNewEntity(tower);
            game.build.play();
            return;
        }

        if (!foundGoldTower) {
            if (game.gold < 10) {
                selectedTroll.say("nog 10 Gold gott.");
                return;
            }
            game.gold -= 10;

            GoldTower tower = new GoldTower();
            tower.setX(selectedTroll.getX());
            tower.setY(selectedTroll.getY());
            addNewEntity(tower);
            game.build.play();
            return;
        }

        selectedTroll.say("ogg GoldTower ag!");

    }

    private void handleSpaceCommand() {

        if (selectedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = findEntitiesAtPoint(selectedTroll.getX(), selectedTroll.getY());
            KEntity highest = null;
            for (KEntity u : under) {
                if (u != selectedTroll) {
                    highest = u;
                }
            }

            if (highest != null) {
                selectedTroll.say("yerg " + highest.toString() + "pug.");
            }

            if (highest instanceof Mushroom) {
                selectedTroll.setPsychedelic(true);
                game.hasEatenMushroom = true;
                highest.destroy();
                game.psych.play();
            }

            if (highest instanceof Mine) {
                selectedTroll.mine();
            }

        }

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
                    selectedTroll.say("ogg " + clickedEntity.toString() + " mog.");
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

    private List<KEntity> entitiesAtScreenPoint(int screenX, int screenY) {

        Vector3 clickLocation = new Vector3(screenX, screenY, 0);
        Vector3 worldLocation = K.graphics.camera.unproject(clickLocation);

        return findEntitiesAtPoint(Math.round(worldLocation.x), Math.round(worldLocation.y));

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
}
