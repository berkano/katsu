package ld32;

import com.badlogic.gdx.Input;
import ld32.entities.EmptyDirt;
import ld32.entities.Mob;
import ld32.entities.MobKillingBlock;
import ld32.entities.Mole;
import panko.*;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends PankoRoomBase {

    private Mole mole;
    public static int numLives;
    public static int level;
    private static long lastRestart = Panko.currentTime();
    public static int poop;

    @Override
    public void start() {
        super.start();

        numLives = LD32Settings.startLives;
        level = LD32Settings.startLevel;
        poop = LD32Settings.startPoop;
        
        String mapName = "ld32";
        PankoTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        Panko.getMainCamera().viewportHeight = 768 / 4;
        Panko.getMainCamera().viewportWidth = 1024 / 4;

        createInstancesAtAll(Mob.class, EmptyDirt.class);

        bringAllInstancesToFront(MobKillingBlock.class);
        bringAllInstancesToFront(Mob.class);

        mole = (Mole) firstInstanceOfClass(Mole.class);
        bringEntityToFront(mole);
        mole.update();

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));

        if (LD32Settings.startWithPausedHelp) {
            Panko.getUI().setShowingHelp(true);
            Panko.pauseGame();
        }

        if (LD32Settings.startWithMusic) LD32Sounds.toggleMusic();

    }

    @Override
    public void render() {
        super.render();
        if (Panko.isKeyDown(Input.Keys.R)) {
            if (lastRestart < Panko.currentTime() - 5000) {
                lastRestart = Panko.currentTime();
                LD32Sounds.game_restart.play();
                start();
            }
        }
    }

    @Override
    public void update() {
        super.update();

        bringAllInstancesToFront(Mob.class);

        if (Panko.isKeyDown(Input.Keys.W)) mole.moveRequested(PankoDirection.UP);
        if (Panko.isKeyDown(Input.Keys.S)) mole.moveRequested(PankoDirection.DOWN);
        if (Panko.isKeyDown(Input.Keys.A)) mole.moveRequested(PankoDirection.LEFT);
        if (Panko.isKeyDown(Input.Keys.D)) mole.moveRequested(PankoDirection.RIGHT);
        if (Panko.isKeyDown(Input.Keys.SPACE)) mole.digRequested();
        if (Panko.isKeyDown(Input.Keys.ENTER)) mole.poopRequested();

        updateUITopText();

        if (numLives <= 0) {
            Panko.getUI().clearText();
            Panko.getUI().writeText("GAME OVER! Press R to restart.");
            Panko.pauseGame();
        }

    }

    private void updateUITopText() {

        String topText = "Level: " + level + "  Lives: " + numLives + " Poopmeter: " + poop + "/" + LD32Settings.maxPoop;
        Panko.getUI().setTopText(topText);

    }

}