package ld34;

import katsu.KGame;
import katsu.KRoom;
import katsu.KUI;

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
        return result;

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
