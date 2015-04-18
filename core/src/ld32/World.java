package ld32;

import com.badlogic.gdx.Input;
import ld32.entities.EmptyDirt;
import ld32.entities.Mob;
import ld32.entities.Mole;
import panko.*;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends PankoRoomBase {

    private Mole mole;

    @Override
    public void start() {
        super.start();
        String mapName = "ld32";
        PankoTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        Panko.getMainCamera().viewportHeight = 768 / 4;
        Panko.getMainCamera().viewportWidth = 1024 / 4;

        createInstancesBehindAll(Mob.class, EmptyDirt.class);

        bringAllInstancesToFront(Mob.class);

        mole = (Mole)firstInstanceOfClass(Mole.class);
        bringEntityToFront(mole);
        mole.update();

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));
        Panko.getUI().setShowingHelp(true);
        Panko.pauseGame();
    }

    @Override
    public void update() {
        super.update();
        if (Panko.isKeyDown(Input.Keys.W)) mole.moveRequested(PankoDirection.UP);
        if (Panko.isKeyDown(Input.Keys.S)) mole.moveRequested(PankoDirection.DOWN);
        if (Panko.isKeyDown(Input.Keys.A)) mole.moveRequested(PankoDirection.LEFT);
        if (Panko.isKeyDown(Input.Keys.D)) mole.moveRequested(PankoDirection.RIGHT);

    }

    @Override
    public boolean keyDown(int keycode) {
//        if (keycode == Input.Keys.W) {
//            mole.moveRequested(PankoDirection.UP);
//            return true;
//        }
//        if (keycode == Input.Keys.A) {
//            mole.moveRequested(PankoDirection.LEFT);
//            return true;
//        }
//        if (keycode == Input.Keys.S) {
//            mole.moveRequested(PankoDirection.DOWN);
//            return true;
//        }
//        if (keycode == Input.Keys.D) {
//            mole.moveRequested(PankoDirection.RIGHT);
//            return true;
//        }
        return super.keyDown(keycode);
    }
}
