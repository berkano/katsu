package ld32;

import ld32.entities.Mole;
import panko.Panko;
import panko.PankoResource;
import panko.PankoRoomBase;
import panko.PankoTmxHelper;

/**
 * Created by shaun on 12/04/2015.
 */
public class World extends PankoRoomBase {

    @Override
    public void start() {
        super.start();
        String mapName = "ld32";
        PankoTmxHelper.addEntitiesToRoomFromMap(mapName, this);

        Panko.getMainCamera().viewportHeight = 768 / 4;
        Panko.getMainCamera().viewportWidth = 1024 / 4;

        Mole mole = (Mole)firstInstanceOfClass(Mole.class);
        if (mole != null) {
            mole.update();
        }

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));
        Panko.getUI().setShowingHelp(true);
        Panko.pauseGame();
    }

}
