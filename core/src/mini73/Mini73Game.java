package mini73;

import katsu.K;
import katsu.KRoom;
import katsu.KRunner;
import ld37wu.LD37wuSounds;
import ld37wu.LD37wuWorld;
import ld37wu.entities.Baby;
import ld37wu.entities.Present;
import ld37wu.entities.Santa;
import ld37wu.entities.Tree;
import mini73.rooms.MainRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class Mini73Game extends KRunner {

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
        rooms.add(new MainRoom());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "mini73";
    }

    @Override
    public List<Class> getClassLookup() {
        return K.utils.scanTiledEntityClasses("mini73");
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
