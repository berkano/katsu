package ld38;

import katsu.K;

/**
 * Created by shaun on 22/04/2017.
 */
public class DevHelper {

    public static boolean playMusicOnStart = K.settings.isProduction();
    public static boolean showHelpOnStart = K.settings.isProduction();
    public static boolean cheatResources = !K.settings.isProduction();
    public static boolean skipWallRule = !K.settings.isProduction();
    public static boolean allTrollsPsychedOnStart = !K.settings.isProduction();
}
