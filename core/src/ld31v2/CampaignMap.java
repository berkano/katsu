package ld31v2;

import ld31v2.entities.Selection;
import panko.PankoRoomBase;
import panko.PankoTmxHelper;

/**
 * Created by shaun on 06/12/2014.
 */
public class CampaignMap extends PankoRoomBase {

    private Selection selection;

    @Override
    public void start() {
        super.start();
        PankoTmxHelper.addEntitiesToRoomFromMap("map", this);
        selection = (Selection)firstInstanceOfClass(Selection.class);
    }

    public void showSelectionAt(int x, int y) {
        selection.setX(x);
        selection.setY(y);
    }
}