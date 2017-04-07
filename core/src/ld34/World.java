package ld34;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import katsu.*;
import ld34.entities.Land;
import ld34.entities.Snowman;
import ld34.entities.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoom {

    private boolean hasStartedMusicAtLeastOnce = false;
    private HashMap<String, Long> onlyEveryTracker = new HashMap<String, Long>();

    Logger logger = LoggerFactory.getLogger(KRoom.class);

    public World() {
        super();
    }

    private long lastRestart = System.currentTimeMillis();

    private boolean doneFirstUpdate = false;

    int lastClickedX = 0;
    int lastClickedY = 0;
    boolean plantingMode = false;

    Snowman player;

    @Override
    public void start() {
        super.start();

        String mapName = "ld34";
        wipeData();
        loadFromTiledMap(mapName);

        K.graphics.camera.viewportHeight = K.settings.getWindowHeight() / 4;
        K.graphics.camera.viewportWidth = K.settings.getWindowWidth() / 4;

        K.input.getMultiplexer().addProcessor(this);

        player = (Snowman) firstInstanceOfClass(Snowman.class);

        if (LD34Settings.get().startWithPausedHelp) {
            K.obsolete.text.showHelp();
        }

        K.obsolete.ui.clearText();
    }

    @Override
    public void render() {
        super.render();

        if (K.input.isKeyDown(Input.Keys.R)) {
            if (lastRestart < K.utils.currentTime() - 2000) {
                lastRestart = K.utils.currentTime();
                start();
            }
        }

    }


    @Override
    public boolean keyDown(int keycode) {

        if (keycode == Input.Keys.M) {
            LD34Sounds.toggleMusic();
            return true;
        }

        if (keycode == Input.Keys.P) {

            plantingMode = !plantingMode;
            if (plantingMode) {
                K.obsolete.ui.writeText("OK I'll plant trees where you click!");
            } else {
                K.obsolete.ui.writeText("OK I won't plant trees unless you ask me again!");
            }

            return true;

        }

        return false;
    }

    @Override
    public void update() {

        super.update();

        K.obsolete.text.setTop("Money: £" + player.getMoney());

        if (!doneFirstUpdate) {
            if (LD34Settings.get().startPaused) {
                K.runner.pauseGame();
            }
        }

        doneFirstUpdate = true;

        if (!K.runner.gamePaused()) {
            if (LD34Settings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD34Sounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }

        setStuffOnFire();
    }

    private void setStuffOnFire() {

        if (!timeFor("setStuffOnFire", 1000L)) return;

        // approx once every 30 secs
        if (K.random.nextInt(LD34Settings.fireChance) != 0) return;

        Tree victim = (Tree) pickAny(Tree.class);

        if (victim == null) return;

        victim.setOnFire();


    }

    private KEntity pickAny(Class clazz) {

        List<KEntity> picks = new ArrayList<KEntity>();

        for (KEntity e : getEntities()) {
            if (clazz.isInstance(e)) {
                picks.add(e);
            }
        }

        Collections.shuffle(picks);

        if (picks.size() > 0) {
            return picks.get(0);
        } else {
            return null;
        }


    }

    private boolean timeFor(String key, Long interval) {

        Long lastOccurred = onlyEveryTracker.get(key);

        if (lastOccurred == null) {
            onlyEveryTracker.put(key, K.utils.currentTime());
            return false;
        }

        if (lastOccurred < K.utils.currentTime() - interval) {
            onlyEveryTracker.put(key, K.utils.currentTime());
            return true;
        }

        return false;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = K.graphics.camera.unproject(new Vector3(screenX, screenY, 0));
        logger.info("World detected touchDown at " + screenX + "," + screenY);
        logger.info("World pos is " + worldPos.toString());
        ArrayList<KEntity> entities = findEntitiesAtPoint(Math.round(worldPos.x), Math.round(worldPos.y));

        boolean foundTree = false;
        boolean foundLand = false;
        boolean foundMe = false;

        for (KEntity e : entities) {

            lastClickedX = e.getGrid().getX();
            lastClickedY = e.getGrid().getY();

            logger.info("There is a " + e.getClass().getSimpleName() + " at this point");
            if (e instanceof Tree) {
                foundTree = true;
            }
            if (e instanceof Land) {
                foundLand = true;
            }
            if (e instanceof Snowman) {
                foundMe = true;
            }
        }

        boolean responded = false;
        K.obsolete.ui.writeText("");

        if (foundMe && !responded) {
            K.obsolete.ui.writeText("Hello!");
            responded = true;
        }
        if (foundTree && !responded) {

            K.obsolete.ui.writeText("OK I'll go chop that!");

            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.CHOP);

            responded = true;
        }
        if (foundLand && !responded) {
            K.obsolete.ui.writeText("OK I'll go and buy that new land!");

            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.BUY_LAND);


            responded = true;
        }
        if (!responded) {

            if (plantingMode) {
                K.obsolete.ui.writeText("OK time to plant some trees! Press P if you want me to stop!");
                player.setHasTarget(true);
                player.setTargetGridX(lastClickedX);
                player.setTargetGridY(lastClickedY);
                player.setTargetAction(Snowman.Action.PLANT);
            } else {
                K.obsolete.ui.writeText("Off I go! If you want me to plant trees, press P!");
                player.setHasTarget(true);
                player.setTargetGridX(lastClickedX);
                player.setTargetGridY(lastClickedY);
                player.setTargetAction(Snowman.Action.WALK);
            }
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }


}