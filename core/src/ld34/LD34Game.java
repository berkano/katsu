package ld34;

import katsu.KGame;
import katsu.KRoom;
import katsu.KUI;
import ld34.entities.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD34Game implements KGame {

    @Override
    public ArrayList<KRoom> getRooms() {

        ArrayList<KRoom> rooms = new ArrayList<KRoom>();
        rooms.add(new World());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "ld34";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {

        HashMap<String, Class> result = new HashMap<String, Class>();
        addClassesTo(result, Grass.class, Land.class, Snowman.class, TreeLarge.class);
        return result;

    }

    private void addClassesTo(HashMap<String, Class> classHashMap, Class... classes) {

        for (Class c: classes) {
            classHashMap.put(c.getSimpleName(), c);
        }

    }

    @Override
    public void toggleMusic() {
        LD34Sounds.toggleMusic();
    }

    @Override
    public KUI createUI() {
        return new LD34UI();
    }
}
