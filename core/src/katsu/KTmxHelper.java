package katsu;

import java.util.ArrayList;

/**
 * Created by shaun on 16/11/2014.
 */
public class KTmxHelper {

    public static KTmxData dataFromMap(String tmxName) {
        return new KTmxData(tmxName, K.getImplementation().getClassLookup()).loadFromMap();
    }

    public static ArrayList<KEntity> entitiesFromMap(String tmxName) {
        return dataFromMap(tmxName).getEntities();
    }

    public static void addEntitiesToRoomFromMap(String tmxName, KRoom room) {
        room.setEntities(entitiesFromMap(tmxName));
        for (KEntity e : room.getEntities()) {
            e.setRoom(room);
        }
    }
}
