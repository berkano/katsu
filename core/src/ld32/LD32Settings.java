package ld32;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Settings {

    public static boolean devMode = true;
    public static boolean startWithMusic = !devMode;
    public static boolean startWithPausedHelp = !devMode;
    public static int maxLives = 5;
    public static int maxPoop = 8;
    public static int startLives = devMode ? 1 : maxLives;
    public static int startLevel = devMode ? 1 : 1;
    public static int startPoop = devMode ? maxPoop : 0;

    public static boolean nerfLava = false;
}
