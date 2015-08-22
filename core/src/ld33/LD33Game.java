package ld33;

import katsu.KGame;
import katsu.KRoom;
import ld33.entities.*;

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
        result.put("Warrior", Warrior.class);
        result.put("Human", Human.class);
        result.put("Dwarf", Dwarf.class);
        result.put("Violet", Violet.class);
        result.put("Door", Door.class);
        result.put("Carpet", Carpet.class);
        result.put("Wall2", Wall2.class);
        result.put("Water", Water.class);
        result.put("Grass", Grass.class);
        result.put("Sheep", Sheep.class);
        return result;

    }

    @Override
    public void toggleMusic() {
        LD33Sounds.toggleMusic();
    }
}