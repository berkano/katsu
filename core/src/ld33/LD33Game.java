package ld33;

import katsu.KGame;
import katsu.KRoom;
import katsu.KUI;
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
        result.put("Cat", Cat.class);
        result.put("StairsUp", StairsUp.class);
        result.put("StairsDown", StairsDown.class);
        result.put("Bed", Bed.class);
        result.put("Blood", Blood.class);
        result.put("WaterSlick", WaterSlick.class);
        result.put("OilSlick", OilSlick.class);
        result.put("Tree", Tree.class);
        result.put("Bar", Bar.class);
        result.put("Skeleton", Skeleton.class);
        return result;

    }

    @Override
    public void toggleMusic() {
        LD33Sounds.toggleMusic();
    }

    @Override
    public KUI createUI() {
        return new LD33UI();
    }
}
