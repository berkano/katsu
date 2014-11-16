package pankosample.rooms;

import panko.components.*;

/**
 * Created by shaun on 16/11/2014.
 */
public class StartRoom extends PankoRoomBase implements PankoRoom  {

    @Override
    public void start() {
        PankoLog.debug("StartRoom started");
        entities = PankoTmxHelper.entitiesFromMap("startroom");
    }
}
