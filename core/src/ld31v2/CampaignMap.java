package ld31v2;

import panko.PankoRoomBase;
import panko.PankoTmxHelper;

/**
 * Created by shaun on 06/12/2014.
 */
public class CampaignMap extends PankoRoomBase {

    @Override
    public void start() {
        super.start();
        PankoTmxHelper.addEntitiesToRoomFromMap("map", this);
    }
}
