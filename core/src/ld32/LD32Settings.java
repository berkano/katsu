package ld32;

import com.badlogic.gdx.Input;
import katsu.PankoSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Settings extends PankoSettings {
    @Override
    public String getGameName() {
        return "Noel and the Spiders";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "LD32";
    }

    public static boolean devMode = false;
    public static boolean startWithMusic = !devMode;
    public static boolean startWithPausedHelp = !devMode;
    public static int maxLives = 5;
    public static int maxPoop = 8;
    public static int startLives = devMode ? maxLives : maxLives;
    public static int startLevel = devMode ? 1 : 1;
    public static int startPoop = devMode ? 0 : 0;
    public static boolean teleportToSpecialPosition = false;

    public static boolean nerfLava = false;
    public static long enemyPathFindInterval = 500;
    public static int enemyPathFindingDistance = 8;
    public static boolean displayPathFindingHints = false;

    @Override
    public int getPauseKey() {
        return Input.Keys.ENTER;
    }
}
