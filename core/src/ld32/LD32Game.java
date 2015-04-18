package ld32;

import ld32.entities.*;
import panko.PankoGame;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD32Game implements PankoGame {

    @Override
    public ArrayList<PankoRoom> getRooms() {

        ArrayList<PankoRoom> rooms = new ArrayList<PankoRoom>();
        rooms.add(new World());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "ld32";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {

        HashMap<String, Class> result = new HashMap<String, Class>();
        result.put("Mole", Mole.class);
        result.put("BlackSpider", BlackSpider.class);
        result.put("Worm", Worm.class);
        result.put("RedSpider", RedSpider.class);
        result.put("Dirt", Dirt.class);
        result.put("Rock", Rock.class);
        result.put("Web", Web.class);
        result.put("Lava", Lava.class);
        result.put("EmptyDirt", EmptyDirt.class);
        result.put("Water", Water.class);
        result.put("Sky", Sky.class);
        result.put("Grass", Grass.class);
        result.put("Wall", Wall.class);
        result.put("ClosedDoor", ClosedDoor.class);
        result.put("OpenDoor", OpenDoor.class);

        return result;

    }

    @Override
    public void toggleMusic() {

    }
}
