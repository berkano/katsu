package ld34;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import katsu.*;
import ld34.entities.Land;
import ld34.entities.Snowman;
import ld34.entities.Tree;

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

    int lastClickedX = 0;
    int lastClickedY = 0;

    int money = 0;

    Snowman player;

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

        player = (Snowman)firstInstanceOfClass(Snowman.class);

        if (LD34Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        K.getUI().clearText();

//        K.getUI().setTopText("1 = WALK, 2 = CHOP, 3 = PLANT, 4 = BUY");

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

        K.getUI().setTopText("Money: £" + player.getMoney());

        if (K.isKeyDown(Input.Keys.W)) {
            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.WALK);
        }

        if (K.isKeyDown(Input.Keys.C)) {
            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.CHOP);
        }

        if (K.isKeyDown(Input.Keys.P)) {
            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.PLANT);
        }

        if (K.isKeyDown(Input.Keys.B)) {
            player.setHasTarget(true);
            player.setTargetGridX(lastClickedX);
            player.setTargetGridY(lastClickedY);
            player.setTargetAction(Snowman.Action.BUY_LAND);
        }


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

        boolean foundTree = false;
        boolean foundLand = false;
        boolean foundMe = false;

        for (KEntity e : entities) {

            lastClickedX = e.getGridX();
            lastClickedY = e.getGridY();

            KLog.trace("There is a " + e.getClass().getSimpleName() + " at this point");
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

        boolean explained = false;

        if (foundMe && !explained) {
            K.getUI().writeText("Hello!");
            explained = true;
        }
        if (foundTree && !explained) {
            K.getUI().writeText("Press C to go and CHOP this tree down.");
            explained = true;
        }
        if (foundLand && !explained) {
            K.getUI().writeText("Press B to BUY this land and expand your farm.");
            explained = true;
        }
        if (!explained) {
            K.getUI().writeText("Press W to WALK over to this spot, or P to PLANT a sapling here.");
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }


}