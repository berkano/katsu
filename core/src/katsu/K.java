package katsu;

import com.badlogic.gdx.graphics.FPSLogger;

import java.util.Random;

/**
 * Created by shaun on 16/11/2014.
 */
public class K {

    public static Random random = new Random();
    public static KLogger logger = new KLogger();
    public static KResource resource = new KResource();
    public static KUtils utils = new KUtils();
    public static KInput input = new KInput();

    // Provided by implementation
    public static KRunner runner;
    public static KSettings settings;
    public static KUI ui;

    private static FPSLogger fpsLogger = new FPSLogger();

    public static void logFPS() {
        if (K.settings.isLogFPS()) {
            fpsLogger.log();
        }
    }
}
