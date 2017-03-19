package mini73;

import katsu.K;
import katsu.KEntity;
import mini73.entities.structures.LandingPad;
import mini73.entities.terrain.*;

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

    public static void populateFrom(List<KEntity> entities) {

        ArrayList<Teleport> surfaceTPs = new ArrayList<Teleport>();
        ArrayList<Teleport> planetTPs = new ArrayList<Teleport>();

        for (KEntity e : entities) {
            if (e instanceof LandingPad) {

                boolean identified = false;
                Teleport newTP = new Teleport(e.getX() / 16, e.getY() / 16, "bug-unassigned");
                teleportArrayList.add(newTP);

                // Surface
                if (getInstanceUnderneath(e, Sun.class) != null) {
                    identified = true;
                    newTP.surface = true;
                    surfaceTPs.add(newTP);
                }

                // Planets
                if (getInstanceUnderneath(e, Grass.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }
                if (getInstanceUnderneath(e, Dirt.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }

                // Station
                if (getInstanceUnderneath(e, Water.class) != null) {
                    identified = true;
                    newTP.dock = true;
                    planetTPs.add(newTP);
                }

                // Helm
                if (getInstanceUnderneath(e, Floor.class) != null) {
                    identified = true;
                    newTP.discovered = true;
                    newTP.name = "Ship Helm";
                }

                if (!identified) {
                    throw new RuntimeException(String.format("Unknown teleport type at %s, %s", e.getX() / 16, e.getY() / 16));
                }


//                teleportArrayList.add(new Teleport(e.x / 16, e.y / 16, "Blarg"));
            }
        }

        // Starting planet
        {
            Teleport randomPlanetTP = planetTPs.get(K.random.nextInt(planetTPs.size()));
            Teleport randomSurfaceTP = surfaceTPs.get(K.random.nextInt(surfaceTPs.size()));
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
            Teleport randomPlanetTP = planetTPs.get(K.random.nextInt(planetTPs.size()));
            Teleport randomSurfaceTP = surfaceTPs.get(K.random.nextInt(surfaceTPs.size()));
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
            Teleport randomSurfaceTP = surfaceTPs.get(K.random.nextInt(surfaceTPs.size()));
            surfaceTPs.remove(randomSurfaceTP);
            t.link(randomSurfaceTP);
            randomSurfaceTP.link(t);
            String name = PlaceNames.placeNames.get(K.random.nextInt(PlaceNames.placeNames.size()));
            PlaceNames.placeNames.remove(name);
            t.name = name;
            randomSurfaceTP.name = name;
        }


    }

    private static Object getInstanceUnderneath(KEntity e, Class clazz) {
        throw new UnportedCodeException();
    }
}
