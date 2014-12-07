package ld31v2;

import ld31v2.entities.*;
import panko.PankoGame;
import panko.PankoRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class WarGame implements PankoGame {

    private static HashMap<String, Class> classLookup = new HashMap<String, Class>();

    private static CampaignMap campaignMap;

    public static CampaignMap getRoom() {
        return campaignMap;
    }

    static {
        classLookup.put("Grass", Grass.class);
        classLookup.put("Dirt", Dirt.class);
        classLookup.put("Water", Water.class);
        classLookup.put("Tower", Tower.class);
        classLookup.put("WallHorz", WallHorz.class);
        classLookup.put("WallVert", WallVert.class);
        classLookup.put("SoldierP1", SoldierP1.class);
        classLookup.put("SoldierP2", SoldierP2.class);
        classLookup.put("SoldierP3", SoldierP3.class);
        classLookup.put("Hills", Hills.class);
        classLookup.put("Mountains", Mountains.class);
        classLookup.put("Selection", Selection.class);
    }

    public WarGame() {
    }

    @Override
    public ArrayList<PankoRoom> getRooms() {

        campaignMap = new CampaignMap();

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        campaignMap
                )
        );
    }

    @Override
    public String getResourceRoot() {
        return "ld31v2";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return classLookup;
    }
}
