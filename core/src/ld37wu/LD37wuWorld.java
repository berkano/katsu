package ld37wu;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import katsu.K;
import katsu.KEntity;
import katsu.KRoom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 12/04/2015.
 */
public class LD37wuWorld extends KRoom {

    private boolean hasStartedMusicAtLeastOnce = false;
    private HashMap<String, Long> onlyEveryTracker = new HashMap<String, Long>();

    public LD37wuWorld() {
        super();
    }

    private long lastRestart = System.currentTimeMillis();

    private boolean doneFirstUpdate = false;

    int lastClickedX = 0;
    int lastClickedY = 0;

    @Override
    public void start() {
        super.start();

        String mapName = "ld37wu";
        wipeData();
        loadFromTiledMap(mapName);

        K.graphics.camera.viewportHeight = K.settings.getWindowHeight() / 4;
        K.graphics.camera.viewportWidth = K.settings.getWindowWidth() / 4;

        K.input.getMultiplexer().addProcessor(this);

        if (LD37wuSettings.get().startWithPausedHelp) {
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
            LD37wuSounds.toggleMusic();
            return true;
        }

        return false;
    }

    @Override
    public void update() {

        super.update();

        if (!doneFirstUpdate) {
            if (LD37wuSettings.get().startPaused) {
                K.runner.pauseGame();
            }
        }

        doneFirstUpdate = true;

        if (!K.runner.gamePaused()) {
            if (LD37wuSettings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD37wuSounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }

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
        K.logger.trace("World detected touchDown at " + screenX + "," + screenY);
        K.logger.trace("World pos is " + worldPos.toString());
        ArrayList<KEntity> entities = findEntitiesAtPoint(Math.round(worldPos.x), Math.round(worldPos.y));

        for (KEntity e : entities) {

            lastClickedX = e.getGrid().getX();
            lastClickedY = e.getGrid().getY();

            K.logger.trace("There is a " + e.getClass().getSimpleName() + " at this point");
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }


}