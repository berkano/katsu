package ld33;

import katsu.KGame;
import katsu.KRoom;
import ld33.entities.Floor;
import ld33.entities.Monster;
import ld33.entities.Wall;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD33Game implements KGame {

    @Override
    public ArrayList<KRoom> getRooms() {

        ArrayList<KRoom> rooms = new ArrayList<KRoom>();
        rooms.add(new World());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "ld33";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {

        HashMap<String, Class> result = new HashMap<String, Class>();
        result.put("Monster", Monster.class);
        result.put("Wall", Wall.class);
        result.put("Floor", Floor.class);
        return result;

    }

    @Override
    public void toggleMusic() {
        LD33Sounds.toggleMusic();
    }
}
