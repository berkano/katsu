package ld32;

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

        Panko.getUI().setHelpText(PankoResource.loadText("help.txt"));
        Panko.getUI().setShowingHelp(true);
        Panko.pauseGame();
    }

}
