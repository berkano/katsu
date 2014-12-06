package ld31;

import panko.PankoGame;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD31PankoGame implements PankoGame {

    public LD31PankoGame() {
    }

    @Override
    public ArrayList<PankoRoom> getRooms() {

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        new Universe()
                )
        );
    }

    @Override
    public String getResourceRoot() {
        return "ld31";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return null;
    }
}
