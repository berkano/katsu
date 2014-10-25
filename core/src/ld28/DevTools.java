package ld28;

import katsu.Entity;
import ld28.entities.mobs.PlayerPerson;

/**
 * Created by shaun on 10/10/2014.
 */
public class DevTools {

    public static boolean freeMoveCheat = false;
    public static boolean superZoomOut = false;
    public static boolean noFuelLimit = false;
    public static int moveFactor = 1;

    public static boolean devMode = false;

    static {
        if (devMode) {
            freeMoveCheat = true;
            superZoomOut = true;
            noFuelLimit = true;
            moveFactor = 10;
        }
    }

    public static void entityClicked() {
        return;
    }

    public static void entityMoving(Entity entity) {
        if (entity instanceof PlayerPerson) {
            return;
        }
    }

    public static void todo(String s) {
        return;
    }
}
