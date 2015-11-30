package ld34;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import katsu.*;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoom {

    private boolean hasStartedMusicAtLeastOnce = false;

    public World() {
        super();
    }

    private long lastRestart = System.currentTimeMillis();

    @Override
    public void start() {
        super.start();

        LD34UI ui = (LD34UI)K.getUI();

        String mapName = "ld34";
        wipeData();
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        K.getUI().getMainCamera().viewportHeight = K.getWindowHeight() / 4;
        K.getUI().getMainCamera().viewportWidth = K.getWindowWidth() / 4;

        K.getUI().setHelpText(KResource.loadText("help.txt"));

        if (LD34Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        K.getUI().clearText();

        if (LD34Settings.get().startPaused) {
            K.pauseGame();
        }

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


        if (!K.gamePaused()) {
            if (LD34Settings.get().startWithMusic) {
                if (!hasStartedMusicAtLeastOnce) {
                    LD34Sounds.playMusic();
                    hasStartedMusicAtLeastOnce = true;
                }
            }

        }
    }

}