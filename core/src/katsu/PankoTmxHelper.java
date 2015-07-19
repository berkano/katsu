package katsu;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoTmxHelper {

    public static PankoTmxData dataFromMap(String tmxName) {
        return new PankoTmxData(tmxName, Panko.getImplementation().getClassLookup()).loadFromMap();
    }

    public static ArrayList<PankoEntity> entitiesFromMap(String tmxName) {
        return dataFromMap(tmxName).getEntities();
    }

    public static void addEntitiesToRoomFromMap(String tmxName, PankoRoom room) {
        room.setEntities(entitiesFromMap(tmxName));
        for (PankoEntity e : room.getEntities()) {
            e.setRoom(room);
        }
    }
}
