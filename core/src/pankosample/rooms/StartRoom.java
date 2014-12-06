package pankosample.rooms;

import com.badlogic.gdx.physics.box2d.World;
import panko.*;

/**
 * Created by shaun on 16/11/2014.
 */
public class StartRoom extends PankoRoomBase {

    @Override
    public void start() {
        super.start();
        PankoLog.debug("StartRoom started");
        PankoTmxHelper.addEntitiesToRoomFromMap("startroom", this);
    }

    @Override
    public World getWorld() {
        return null;
    }

}
