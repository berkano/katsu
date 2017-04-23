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

/**
 * Created by shaun on 21/04/2017.
 */
public class Map extends KRoom {

    private int lastDragX = 0;
    private int lastDragY = 0;
    private boolean hasDragged = false;

    private Logger logger = LoggerFactory.getLogger(Map.class);

    private TrollCastleGame game;

    private Troll lastClickedTroll = null;

    private TrollCommand currentCommand = TrollCommand.none;

    @Override
    public void update() {
        super.update();

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
        if (character == 'g') {
            handleGoCommand();
        }
        if (character == ' ') {
            handleSpaceCommand();
        }
        if (character == 'b') {
            handleBuildCommand();
        }
        return false;
    }

    private void handleBuildCommand() {
        // do we have a selected troll?
        if (lastClickedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            return;
        }

        // work out what we're on top of, first.
        List<KEntity> under = findEntitiesAtPoint(lastClickedTroll.getX() + 2, lastClickedTroll.getY() + 2);


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
            lastClickedTroll.say("ogg Sand digg mog.");
            return;
        }

        if (!foundWall) {
            Wall wall = new Wall();
            wall.setX(lastClickedTroll.getX());
            wall.setY(lastClickedTroll.getY());
            addNewEntity(wall);
            return;
        }

        if (!foundTower) {
            Tower tower = new Tower();
            tower.setX(lastClickedTroll.getX());
            tower.setY(lastClickedTroll.getY());
            addNewEntity(tower);
            return;
        }

        if (!foundGoldTower) {
            GoldTower tower = new GoldTower();
            tower.setX(lastClickedTroll.getX());
            tower.setY(lastClickedTroll.getY());
            addNewEntity(tower);
            return;
        }

        lastClickedTroll.say("ogg GoldTower ag!");

    }

    private void handleSpaceCommand() {

        if (lastClickedTroll == null) {

            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");

        } else {

            List<KEntity> under = findEntitiesAtPoint(lastClickedTroll.getX(), lastClickedTroll.getY());
            KEntity highest = null;
            for (KEntity u : under) {
                if (u != lastClickedTroll) {
                    highest = u;
                }
            }

            if (highest != null) {
                lastClickedTroll.say("yerg " + highest.toString() + "pug.");
            }

            if (highest instanceof Mushroom) {
                lastClickedTroll.setPsychedelic(true);
                game.hasEatenMushroom = true;
                highest.destroy();
                game.psych.play();
            }

            if (highest instanceof Mine) {
                lastClickedTroll.mine();
            }

        }

    }

    private void handleGoCommand() {
        if (lastClickedTroll == null) {
            game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
        } else {
            lastClickedTroll.say("ogg mog?");
            currentCommand = TrollCommand.go;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        logger.info("Detected touch up");

        if (hasDragged) return false;


        KEntity highestEntity = null;

        List<KEntity> clickedEntities = entitiesAtScreenPoint(screenX, screenY);

        Troll trollBefore = lastClickedTroll;

        for (KEntity e : clickedEntities) {
            logger.info("calling onClick for an instance of " + e.getClass().getSimpleName());
//            e.onClick();
            highestEntity = e;
            if (e instanceof Troll) {
                lastClickedTroll = (Troll)e;
            }
        }


        if (highestEntity != null) {
            if (currentCommand == TrollCommand.go) {
                logger.info("Processing GO command");
                trollBefore.setTargetEntity(highestEntity);
                trollBefore.say("ogg " + highestEntity.toString()+" mog.");
                currentCommand = TrollCommand.none;
            } else {
                highestEntity.onClick();
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
