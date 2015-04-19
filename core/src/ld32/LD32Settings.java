package ld32;

import com.badlogic.gdx.Input;
import panko.PankoSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Settings extends PankoSettings {

    public static boolean devMode = true;
    public static boolean startWithMusic = !devMode;
    public static boolean startWithPausedHelp = !devMode;
    public static int maxLives = 5;
    public static int maxPoop = 8;
    public static int startLives = devMode ? 1 : maxLives;
    public static int startLevel = devMode ? 1 : 1;
    public static int startPoop = devMode ? maxPoop : 0;

    public static boolean nerfLava = false;
    public static long enemyPathFindInterval = 500;
    public static int enemyPathFindingDistance = 8;
    public static boolean displayPathFindingHints = false;

    @Override
    public int getPauseKey() {
        return Input.Keys.ENTER;
    }
}
