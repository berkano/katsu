package ld31v2;

import ld31.Universe;
import panko.PankoGame;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class LD31V2PankoGame implements PankoGame {

    public LD31V2PankoGame() {
    }

    @Override
    public ArrayList<PankoRoom> getRooms() {

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        new CampaignMap()
                )
        );
    }

    @Override
    public String getResourceRoot() {
        return "ld31v2";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return null;
    }
}
