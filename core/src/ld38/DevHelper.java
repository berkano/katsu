package ld38;

import katsu.K;

/**
 * Created by shaun on 22/04/2017.
 */
public class DevHelper {

    public static boolean playMusicOnStart = K.settings.isProduction();
    public static boolean showHelpOnStart = K.settings.isProduction();
    public static boolean cheatResources = !K.settings.isProduction();
    public static boolean skipWallRule = false; // !K.settings.isProduction();
    public static boolean allTrollsPsychedOnStart = !K.settings.isProduction();
    public static boolean xKillsTroll = !K.settings.isProduction();
    public static boolean randomMushroomOnStart = !K.settings.isProduction();
    public static boolean quickMushGrow = !K.settings.isProduction();
}
