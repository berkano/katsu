package ld33;

import com.badlogic.gdx.Input;
import katsu.*;
import ld33.entities.Monster;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoomBase {

    public World() {
        super();
    }
    private Monster player;

    @Override
    public void start() {
        super.start();

        String mapName = "ld33";
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);
        player = (Monster)firstInstanceOfClass(Monster.class);

        K.getMainCamera().viewportHeight = K.getWindowHeight() / 4;
        K.getMainCamera().viewportWidth = K.getWindowWidth() / 4;

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

        if (K.isKeyDown(Input.Keys.W)) player.moveRequested(KDirection.UP);
        if (K.isKeyDown(Input.Keys.S)) player.moveRequested(KDirection.DOWN);
        if (K.isKeyDown(Input.Keys.A)) player.moveRequested(KDirection.LEFT);
        if (K.isKeyDown(Input.Keys.D)) player.moveRequested(KDirection.RIGHT);


    }

}