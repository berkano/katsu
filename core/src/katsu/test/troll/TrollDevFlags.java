package katsu.test.troll;

import katsu.K;

/**
 * Created by shaun on 22/04/2017.
 */
public class TrollDevFlags {

    static boolean playMusicOnStart = K.settings.isProduction();
    static boolean showHelpOnStart = K.settings.isProduction();
    static boolean cheatResources = !K.settings.isProduction();
    static boolean skipWallRule = false;
    public static boolean allTrollsPsychedOnStart = !K.settings.isProduction();
    static boolean randomMushroomOnStart = !K.settings.isProduction();
    public static boolean quickMushGrow = !K.settings.isProduction();
}
