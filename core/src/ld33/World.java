package ld33;

import com.badlogic.gdx.Input;
import katsu.*;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoomBase {

    public World() {
        super();
    }

    @Override
    public void start() {
        super.start();

        String mapName = "ld33";
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        K.getMainCamera().viewportHeight = 768 / 4;
        K.getMainCamera().viewportWidth = 1024 / 4;

        K.getUI().setHelpText(KResource.loadText("help.txt"));

        if (LD33Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        if (LD33Settings.get().startWithMusic) LD33Sounds.playMusic();

        K.getUI().clearText();

        if (LD33Settings.get().startPaused) {
            K.pauseGame();
        }

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void update() {
        super.update();

    }

}