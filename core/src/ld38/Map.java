package ld38;

import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;
import ld38.entities.Mine;
import ld38.entities.Mushroom;
import ld38.entities.Troll;
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

        // Ensure trolls always on top and get their total XP
        game.xp = 0;
        game.trolls = 0;
        List<KEntity> onTops = new ArrayList<>();
        for (KEntity e: getEntities()) {
            if (e instanceof Troll) {
                Troll t = (Troll) e;
                onTops.add(t);
                game.xp += t.xp;
                game.trolls++;
            }
        }

        for (KEntity e: onTops) {
            getEntities().remove(e);
            getEntities().add(e);
        }

    }

    @Override
    public void start() {

        super.start();
        loadFromTiledMap("test-map");
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
        if (character == 'g') {
            if (lastClickedTroll == null) {
                game.ui.bottomBar.writeLine("[RED]Click on a troll first.");
            } else {
                lastClickedTroll.say("ogg mog?");
                currentCommand = TrollCommand.go;
            }
        }
        if (character == ' ') {
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
                }

                if (highest instanceof Mine) {
                    lastClickedTroll.mine();
                    game.hasMined = true;
                }

            }
        }
        return false;
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
            e.onClick();
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
