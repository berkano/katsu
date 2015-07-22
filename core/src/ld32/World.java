package ld32;

import com.badlogic.gdx.Input;
import ld32.entities.*;
import katsu.*;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends KRoomBase {

    private Mole mole;
    public static int numLives;
    private static long lastRestart = K.currentTime();
    public static int poop;
    private static int waypointX = 0;
    private static int waypointY = 0;

    public static void setWaypointX(int waypointX) {
        World.waypointX = waypointX;
    }

    public static int getWaypointX() {
        return waypointX;
    }

    public World() {
        super();
        setRenderFog(true);
        setFogRadius(180);
    }

    public static void setWaypointY(int waypointY) {
        World.waypointY = waypointY;
    }

    public static int getWaypointY() {
        return waypointY;
    }

    @Override
    public void start() {
        super.start();

        numLives = LD32Settings.get().startLives;
        poop = LD32Settings.get().startPoop;
        
        String mapName = "ld32";
        KTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        K.getMainCamera().viewportHeight = 768 / 4;
        K.getMainCamera().viewportWidth = 1024 / 4;

        createInstancesAtAll(Mob.class, EmptyDirt.class);
        createInstancesAtAll(WayPoint.class, EmptyDirt.class);
        createInstancesAtAll(MobKillingBlock.class, EmptyDirt.class);

        mole = (Mole) firstInstanceOfClass(Mole.class);

        if (waypointX != 0) {
            mole.setX(waypointX);
            mole.setY(waypointY);
        }

        if (LD32Settings.get().teleportToSpecialPosition) {
//            int toX = 34; int toY = 43; // Level 4
//            int toX = 19; int toY = 66; // Level 5
//            int toX = 37; int toY = 85; // Level 6
//            int toX = 64; int toY = 81; // Level 7
            int toX = 74; int toY = 59; // Level 8
//            int toX = 80; int toY = 27; // Win Level
            mole.setX(toX * mole.getWidth());
            mole.setY((99-toY) * mole.getHeight());
        }

        mole.update();

        K.getUI().setHelpText(KResource.loadText("help.txt"));

        if (LD32Settings.get().startWithPausedHelp) {
            K.getUI().setShowingHelp(true);
        }

        if (LD32Settings.get().startWithMusic) LD32Sounds.playMusic();

        K.getUI().clearText();
        updateUITopText();
        if (LD32Settings.get().startPaused) {
            K.pauseGame();
        }

    }

    @Override
    public void render() {

        setFogX(mole.getX());
        setFogY(mole.getY());

        super.render();
        if (K.isKeyDown(Input.Keys.R)) {
            if (lastRestart < K.currentTime() - 5000) {
                lastRestart = K.currentTime();
                LD32Sounds.game_restart.play();
                start();
            }
        }
    }

    @Override
    public void update() {
        super.update();

        if (K.isKeyDown(Input.Keys.W)) moveOrDig(KDirection.UP);
        if (K.isKeyDown(Input.Keys.S)) moveOrDig(KDirection.DOWN);
        if (K.isKeyDown(Input.Keys.A)) moveOrDig(KDirection.LEFT);
        if (K.isKeyDown(Input.Keys.D)) moveOrDig(KDirection.RIGHT);
        if (K.isKeyDown(Input.Keys.P)) mole.poopRequested();

        updateUITopText();

        if (numLives <= 0) {
            K.getUI().clearText();
            K.getUI().writeText("GAME OVER! Press R to restart.");
            K.pauseGame();
        }

    }

    private void moveOrDig(KDirection direction) {
        if (!mole.moveRequested(direction)) {
            mole.digRequested();
        }
    }

    private void updateUITopText() {

        String topText = "Lives: " + numLives + " Poopmeter: " + poop + "/" + LD32Settings.get().maxPoop;
        K.getUI().setTopText(topText);

    }

}