package ld28;

import katsu.Game;
import katsu.UI;
import ld28.entities.mobs.*;
import ld28.entities.resources.*;
import ld28.entities.structures.Door;
import ld28.entities.structures.LandingPad;
import ld28.entities.structures.MetalWall;
import ld28.entities.structures.Wall;
import ld28.entities.terrain.*;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 12/05/13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class LevelManager {

    public static int helpPages = Documentation.numPages;

    public static String nextLevel(String fromLevel) {
        if (fromLevel.equals("0000")) return ("0001");
        if (fromLevel.equals("0001")) return ("0002");
        if (fromLevel.equals("0002")) return ("0003");
        if (fromLevel.equals("0003")) return ("0004");
        if (fromLevel.equals("0004")) return ("0005");
        if (fromLevel.equals("0005")) return ("0000");
        return "";
    }

    public static String tmxForLevelCode(String code) {

        if (code.equals("0000")) return "0000";
        if (code.equals("0001")) return "0001";
        if (code.equals("0002")) return "0002";
        if (code.equals("0003")) return "0003";
        if (code.equals("0004")) return "0004";
        if (code.equals("0005")) return "0005";
        if (code.equals("0006")) return "0006";
        if (code.equals("0007")) return "0007";
        if (code.equals("0008")) return "0008";
        if (code.equals("0009")) return "0009";
        if (code.equals("0010")) return "0010";
        return "";
    }

    public static void showLevelInstructions(UI ui, Game game) {
    }

    public static void showHelp(int helpPage, UI ui) {

        String[] lines = Documentation.getHelpPage(helpPage).split("\n");
        ui.writeText(String.format("*** HELP: Page (%s of %s). Press H for next page. ***", helpPage, helpPages));
        for (String line : lines) {
            ui.writeText(line);
        }

    }

    public static HashMap<String, Class> getTmxClassMapping() {

        HashMap<String, Class> classLookup = new HashMap<String, Class>();

        // Resources
        classLookup.put("Diamond", Diamond.class);
        classLookup.put("Wood", Wood.class);
        classLookup.put("Gold", Gold.class);
        classLookup.put("Iron", Iron.class);
        classLookup.put("LuxuryItem", LuxuryItem.class);
        classLookup.put("Medicine", Medicine.class);
        classLookup.put("Fuel", Fuel.class);
        classLookup.put("Robot", Robot.class);
        classLookup.put("Potato", Potato.class);


        // Terrain
        classLookup.put("Dirt", Dirt.class);
        classLookup.put("Grass", Grass.class);
        classLookup.put("Water", Water.class);
        classLookup.put("Sand", Sand.class);
        classLookup.put("Floor", Floor.class);
        classLookup.put("Asteroid", Asteroid.class);
        classLookup.put("Sun", Sun.class);

        // Structures
        classLookup.put("Wall", Wall.class);
        classLookup.put("Fire", Fire.class);
        classLookup.put("MetalWall", MetalWall.class);
        classLookup.put("Door", Door.class);
        classLookup.put("LandingPad", LandingPad.class);

        //Mobs
        classLookup.put("GreenMan", PlayerPerson.class);
        classLookup.put("RedMan", FriendlyPerson.class);
        classLookup.put("PinkMan", EnemyPerson.class);
        classLookup.put("Ship", Ship.class);
        classLookup.put("Sheep", Sheep.class);
        classLookup.put("EnemyShip", EnemyShip.class);
        classLookup.put("FriendlyShip", FriendlyShip.class);

        return classLookup;

    }
}
