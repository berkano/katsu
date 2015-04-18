package ld32;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Settings {

    public static boolean devMode = true;
    public static boolean startWithMusic = !devMode;
    public static boolean startWithPausedHelp = !devMode;
    public static int startLives = devMode ? 1 : 5;
    public static int startLevel = devMode ? 1 : 1;

}
