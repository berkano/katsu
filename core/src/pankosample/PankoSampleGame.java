package pankosample;

import panko.PankoGame;
import panko.PankoRoom;
import pankosample.entities.Floor;
import pankosample.entities.Robot;
import pankosample.entities.Wall;
import pankosample.rooms.StartRoom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoSampleGame implements PankoGame {

    private static HashMap<String, Class> classLookup;

    static {
        classLookup = new HashMap<String, Class>();
        classLookup.put("Robot", Robot.class);
        classLookup.put("Floor", Floor.class);
        classLookup.put("Wall", Wall.class);
    }

    public PankoSampleGame() {
    }

    @Override
    public ArrayList<PankoRoom> getRooms() {

        return new ArrayList<PankoRoom>(
                Arrays.asList(
                        new StartRoom()
                )
        );
    }

    @Override
    public String getResourceRoot() {
        return "pankosample";
    }

    @Override
    public HashMap<String, Class> getClassLookup() {
        return classLookup;
    }
}
