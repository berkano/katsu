package ld28;

import katsu.Entity;
import katsu.Katsu;
import katsu.Util;
import ld28.entities.structures.LandingPad;
import ld28.entities.terrain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 15/12/13.
 */
public class TeleportMap {

    public static ArrayList<Teleport> teleportArrayList = new ArrayList<Teleport>();

//    static {
//        build();
//    }

//    public static void build() {
//        teleportArrayList.add(new Teleport(46, 36, "Helm"));
//        newPair("Planet1", 44, 19, "Landing1", 31, 23);
//    }

    public static void newPair(String name1, int x1, int y1, String name2, int x2, int y2) {
        Teleport t1 = new Teleport(x1, y1, name1);
        Teleport t2 = new Teleport(x2, y2, name2);
        t1.link(t2);
        t2.link(t1);
        teleportArrayList.add(t1);
        teleportArrayList.add(t2);
    }

    public static Teleport findByName(String name) {
        for (Teleport t : teleportArrayList) {
            if (t.name.equals(name)) {
                return t;
            }
        }
        return null;
    }

    public static void populateFrom(List<Entity> entities) {

        ArrayList<Teleport> surfaceTPs = new ArrayList<Teleport>();
        ArrayList<Teleport> planetTPs = new ArrayList<Teleport>();

        for (Entity e : entities) {
            if (e instanceof LandingPad) {

                boolean identified = false;
                Teleport newTP = new Teleport(e.x / 16, e.y / 16, "bug-unassigned");
                teleportArrayList.add(newTP);

                // Surface
                if (e.getInstanceUnderneath(Sun.class) != null) {
                    identified = true;
                    newTP.surface = true;
                    surfaceTPs.add(newTP);
                }

                // Planets
                if (e.getInstanceUnderneath(Grass.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }
                if (e.getInstanceUnderneath(Dirt.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }

                // Station
                if (e.getInstanceUnderneath(Water.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }

                // Helm
                if (e.getInstanceUnderneath(Floor.class) != null) {
                    identified = true;
                    newTP.discovered = true;
                    newTP.name = "Ship Helm";
                }

                if (!identified) {
                    throw new RuntimeException(String.format("Unknown teleport type at %s, %s", e.x / 16, e.y / 16));
                }


//                teleportArrayList.add(new Teleport(e.x / 16, e.y / 16, "Blarg"));
            }
        }

        // Starting planet
        {
            Teleport randomPlanetTP = planetTPs.get(Katsu.random.nextInt(planetTPs.size()));
            Teleport randomSurfaceTP = surfaceTPs.get(Katsu.random.nextInt(surfaceTPs.size()));
            planetTPs.remove(randomPlanetTP);
            surfaceTPs.remove(randomSurfaceTP);
            randomPlanetTP.name = "Xorx";
            randomSurfaceTP.name = "Xorxia";
            randomSurfaceTP.discovered = true;
            randomPlanetTP.discovered = true;
            randomPlanetTP.link(randomSurfaceTP);
            randomSurfaceTP.link(randomPlanetTP);
        }

        // Home planet
        {
            Teleport randomPlanetTP = planetTPs.get(Katsu.random.nextInt(planetTPs.size()));
            Teleport randomSurfaceTP = surfaceTPs.get(Katsu.random.nextInt(surfaceTPs.size()));
            planetTPs.remove(randomPlanetTP);
            surfaceTPs.remove(randomSurfaceTP);
            randomPlanetTP.name = "Earth";
            randomSurfaceTP.name = "London";
            randomPlanetTP.link(randomSurfaceTP);
            randomSurfaceTP.link(randomPlanetTP);
        }

        // Randomly link up all the rest
        Util.log(String.format("Found %s remaining planet TPs and %s remaining surface TPs", planetTPs.size(), surfaceTPs.size()));
        for (Teleport t : planetTPs) {
            Teleport randomSurfaceTP = surfaceTPs.get(Katsu.random.nextInt(surfaceTPs.size()));
            surfaceTPs.remove(randomSurfaceTP);
            t.link(randomSurfaceTP);
            randomSurfaceTP.link(t);
            String name = PlaceNames.placeNames.get(Katsu.random.nextInt(PlaceNames.placeNames.size()));
            PlaceNames.placeNames.remove(name);
            t.name = name;
            randomSurfaceTP.name = name;
        }


    }
}