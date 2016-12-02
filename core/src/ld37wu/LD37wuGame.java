package ld37wu;

import katsu.K;
import katsu.KRoom;
import katsu.KRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD37wuGame extends KRunner {

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public ArrayList<KRoom> getRooms() {

        ArrayList<KRoom> rooms = new ArrayList<KRoom>();
        rooms.add(new LD37wuWorld());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "ld37wu";
    }

    @Override
    public List<Class> getClassLookup() {

        return K.utils.buildClassList(
//                Grass.class, Land.class, Snowman.class, TreeLarge.class, TreeMedium.class, TreeSmall.class, Sapling.class,
//                Fire.class, Water.class, Dirt.class, SnowmanGold.class, SnowmanScarf.class
        );
    }

    private void addClassesTo(HashMap<String, Class> classHashMap, Class... classes) {

        for (Class c: classes) {
            classHashMap.put(c.getSimpleName(), c);
        }

    }

    @Override
    public void toggleMusic() {
        LD37wuSounds.toggleMusic();
    }

}
