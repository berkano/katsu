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

        createInstancesAtAll(Mob.class, EmptyDirt.class);

        bringAllInstancesToFront(Mob.class);

        mole = (Mole) firstInstanceOfClass(Mole.class);
        bringEntityToFront(mole);
        mole.update();

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));
        Panko.getUI().setShowingHelp(true);
        Panko.pauseGame();

        LD32Sounds.toggleMusic();
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

    }

}