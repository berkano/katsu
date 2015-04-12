package ld32;

import katsu.Room;
import panko.PankoGame;
import panko.PankoRoom;
import panko.PankoRoomBase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD32Game implements PankoGame {

    @Override
    public ArrayList<PankoRoom> getRooms() {

        ArrayList<PankoRoom> rooms = new ArrayList<PankoRoom>();
        rooms.add(new EmptyRoom());
        return rooms;

    }

    @Override
    public String getResourceRoot() {
        return "ld32";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return null;
    }

    @Override
    public void toggleMusic() {

    }
}
