package katsu;

public class Settings {

    public static boolean devMode = getDevMode();

    public static boolean perfMode = false && devMode;

    // Debug flags - only active in dev mode
    public static boolean collisionDebug = false && devMode;
    public static boolean damageDebug = false && devMode;
    public static boolean cheatMode = true && devMode;
    public static String startLevel = "0000";
    public static boolean showFPS = true && devMode;
    public static boolean customDayTick = true && devMode;
    public static boolean debugObjectives = false && devMode;

    // General settings
    public static int targetFrameRate = perfMode ? 1000 : 60;
    public static int ticksPerSecond = 20;
    public static boolean vsync = false;
    public static int hres = 1024;
    public static int vres = 768;
    public static String gameName = "gameName";
    public static String gameAuthor = "berkano";
    public static String gameDescription = "LD28";
    public static boolean grabMouse = false;
    public static int tileWidth = 16;
    public static int tileHeight = 16;
    public static String resourceFolder = "src/game/resources/game/";
    public static int pixelScale = 1;
    public static boolean startWithMusic = !devMode;
    public static boolean logInfo = true;
    public static boolean logDebug = devMode;
    public static boolean logTrace = false;
    public static boolean logError = true;
    public static boolean loggingEnabled = true || devMode;
    public static boolean pinCodesEnabled = false;

    private static boolean getDevMode() {
        try {
            if ((System.getProperty("devMode")) != null) {
                Logger.info("devMode=true");
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }
}
