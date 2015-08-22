package ld33;

import com.badlogic.gdx.Gdx;
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
        player = (Monster) firstInstanceOfClass(Monster.class);

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

//        setRenderFog(true);
//        setFogRadius(96);
//        setFogX(player.getX());
//        setFogY(player.getY());

        boolean somethingHappened = false;
        KDirection directionToMove = null;

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) directionToMove = KDirection.UP;
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) directionToMove = KDirection.LEFT;
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) directionToMove = KDirection.DOWN;
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) directionToMove = KDirection.RIGHT;

        if (!K.isKeyDown(Input.Keys.SHIFT_LEFT) && !K.isKeyDown(Input.Keys.SHIFT_RIGHT)) {

            if (K.isKeyDown(Input.Keys.W)) directionToMove = KDirection.UP;
            if (K.isKeyDown(Input.Keys.A)) directionToMove = KDirection.LEFT;
            if (K.isKeyDown(Input.Keys.S)) directionToMove = KDirection.DOWN;
            if (K.isKeyDown(Input.Keys.D)) directionToMove = KDirection.RIGHT;


        }


        if (directionToMove != null) {
            if (player.moveRequested(directionToMove)) {
                somethingHappened = true;
            }
        }

        if (somethingHappened) {
            K.setLastRogueUpdate(System.currentTimeMillis());
        }

    }

}