package pankosample.rooms;

import panko.*;

/**
 * Created by shaun on 16/11/2014.
 */
public class StartRoom extends PankoRoomBase {

    @Override
    public void start() {
        super.start();
        PankoLog.debug("StartRoom started");
        entities = PankoTmxHelper.entitiesFromMap("startroom");
    }

}
