package ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import katsu.*;

import java.util.ArrayList;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoom {

    private boolean hasStartedMusicAtLeastOnce = false;

    public World() {
        super();
    }

    private long lastRestart = System.currentTimeMillis();

    private boolean doneFirstUpdate = false;

    @Override
    public void start() {
        super.start();

        LD34UI ui = (LD34UI)K.getUI();

        String mapName = "ld34";
        wipeData();
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        K.getUI().getMainCamera().viewportHeight = K.getWindowHeight() / 4;
        K.getUI().getMainCamera().viewportWidth = K.getWindowWidth() / 4;

        K.getInputMultiplexer().addProcessor(this);

        K.getUI().setHelpText(KResource.loadText("help.txt"));

        if (LD34Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        K.getUI().clearText();


    }

    @Override
    public void render() {
        super.render();

        if (K.isKeyDown(Input.Keys.R)) {
            if (lastRestart < K.currentTime() - 2000) {
                lastRestart = K.currentTime();
//                LD34Sounds.restart.play();
                start();
            }
        }

    }

    @Override
    public void update() {

        super.update();

        if (!doneFirstUpdate) {
            if (LD34Settings.get().startPaused) {
                K.pauseGame();
            }
        }

        doneFirstUpdate = true;

        if (!K.gamePaused()) {
            if (LD34Settings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD34Sounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldPos = K.getUI().getMainCamera().unproject(new Vector3(screenX, screenY, 0));
        KLog.trace("World detected touchDown at " + screenX + "," + screenY);
        KLog.trace("World pos is " + worldPos.toString());
        ArrayList<KEntity> entities = findEntitiesAtPoint(Math.round(worldPos.x), Math.round(worldPos.y));
        for (KEntity e : entities) {
            KLog.trace("There is a " + e.getClass().getSimpleName() + " at this point");
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }


}