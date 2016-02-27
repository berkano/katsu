package ld34;

import katsu.*;
import ld34.entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD34Game extends KGameRunner {

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
    public List<Class> getClassLookup() {

        return K.utils.buildClassList(
                Grass.class, Land.class, Snowman.class, TreeLarge.class, TreeMedium.class, TreeSmall.class, Sapling.class,
                Fire.class, Water.class, Dirt.class, SnowmanGold.class, SnowmanScarf.class
        );
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
