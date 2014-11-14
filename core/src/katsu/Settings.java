package katsu;

public class Settings {

    private static boolean devMode = getDevMode();

    private static boolean perfMode = false && isDevMode();

    // Debug flags - only active in dev mode
    private static boolean collisionDebug = false && isDevMode();
    private static boolean damageDebug = false && isDevMode();
    private static boolean cheatMode = true && isDevMode();
    private static String startLevel = "0000";
    private static boolean showFPS = true && isDevMode();
    private static boolean customDayTick = true && isDevMode();
    private static boolean debugObjectives = false && isDevMode();

    // General settings
    private static int targetFrameRate = isPerfMode() ? 1000 : 60;
    private static int ticksPerSecond = 20;
    private static boolean vsync = false;
    private static int hres = 1024;
    private static int vres = 768;
    private static String gameName = "gameName";
    private static String gameAuthor = "author";
    private static String gameDescription = "LDXX";
    private static boolean grabMouse = false;
    private static int tileWidth = 16;
    private static int tileHeight = 16;
    private static String resourceFolder = "src/game/resources/game/";
    private static int pixelScale = 1;
    private static boolean startWithMusic = !isDevMode();
    private static boolean logInfo = true;
    private static boolean logDebug = isDevMode();
    private static boolean logTrace = false;
    private static boolean logError = true;
    private static boolean loggingEnabled = true || isDevMode();
    private static boolean pinCodesEnabled = false;

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

    public static boolean isDevMode() {
        return devMode;
    }

    public static void setDevMode(boolean devMode) {
        Settings.devMode = devMode;
    }

    public static boolean isPerfMode() {
        return perfMode;
    }

    public static void setPerfMode(boolean perfMode) {
        Settings.perfMode = perfMode;
    }

    public static boolean isCollisionDebug() {
        return collisionDebug;
    }

    public static void setCollisionDebug(boolean collisionDebug) {
        Settings.collisionDebug = collisionDebug;
    }

    public static boolean isDamageDebug() {
        return damageDebug;
    }

    public static void setDamageDebug(boolean damageDebug) {
        Settings.damageDebug = damageDebug;
    }

    public static boolean isCheatMode() {
        return cheatMode;
    }

    public static void setCheatMode(boolean cheatMode) {
        Settings.cheatMode = cheatMode;
    }

    public static String getStartLevel() {
        return startLevel;
    }

    public static void setStartLevel(String startLevel) {
        Settings.startLevel = startLevel;
    }

    public static boolean isShowFPS() {
        return showFPS;
    }

    public static void setShowFPS(boolean showFPS) {
        Settings.showFPS = showFPS;
    }

    public static boolean isCustomDayTick() {
        return customDayTick;
    }

    public static void setCustomDayTick(boolean customDayTick) {
        Settings.customDayTick = customDayTick;
    }

    public static boolean isDebugObjectives() {
        return debugObjectives;
    }

    public static void setDebugObjectives(boolean debugObjectives) {
        Settings.debugObjectives = debugObjectives;
    }

    public static int getTargetFrameRate() {
        return targetFrameRate;
    }

    public static void setTargetFrameRate(int targetFrameRate) {
        Settings.targetFrameRate = targetFrameRate;
    }

    public static int getTicksPerSecond() {
        return ticksPerSecond;
    }

    public static void setTicksPerSecond(int ticksPerSecond) {
        Settings.ticksPerSecond = ticksPerSecond;
    }

    public static boolean isVsync() {
        return vsync;
    }

    public static void setVsync(boolean vsync) {
        Settings.vsync = vsync;
    }

    public static int getHres() {
        return hres;
    }

    public static void setHres(int hres) {
        Settings.hres = hres;
    }

    public static int getVres() {
        return vres;
    }

    public static void setVres(int vres) {
        Settings.vres = vres;
    }

    public static String getGameName() {
        return gameName;
    }

    public static void setGameName(String gameName) {
        Settings.gameName = gameName;
    }

    public static String getGameAuthor() {
        return gameAuthor;
    }

    public static void setGameAuthor(String gameAuthor) {
        Settings.gameAuthor = gameAuthor;
    }

    public static String getGameDescription() {
        return gameDescription;
    }

    public static void setGameDescription(String gameDescription) {
        Settings.gameDescription = gameDescription;
    }

    public static boolean isGrabMouse() {
        return grabMouse;
    }

    public static void setGrabMouse(boolean grabMouse) {
        Settings.grabMouse = grabMouse;
    }

    public static int getTileWidth() {
        return tileWidth;
    }

    public static void setTileWidth(int tileWidth) {
        Settings.tileWidth = tileWidth;
    }

    public static int getTileHeight() {
        return tileHeight;
    }

    public static void setTileHeight(int tileHeight) {
        Settings.tileHeight = tileHeight;
    }

    public static String getResourceFolder() {
        return resourceFolder;
    }

    public static void setResourceFolder(String resourceFolder) {
        Settings.resourceFolder = resourceFolder;
    }

    public static int getPixelScale() {
        return pixelScale;
    }

    public static void setPixelScale(int pixelScale) {
        Settings.pixelScale = pixelScale;
    }

    public static boolean isStartWithMusic() {
        return startWithMusic;
    }

    public static void setStartWithMusic(boolean startWithMusic) {
        Settings.startWithMusic = startWithMusic;
    }

    public static boolean isLogInfo() {
        return logInfo;
    }

    public static void setLogInfo(boolean logInfo) {
        Settings.logInfo = logInfo;
    }

    public static boolean isLogDebug() {
        return logDebug;
    }

    public static void setLogDebug(boolean logDebug) {
        Settings.logDebug = logDebug;
    }

    public static boolean isLogTrace() {
        return logTrace;
    }

    public static void setLogTrace(boolean logTrace) {
        Settings.logTrace = logTrace;
    }

    public static boolean isLogError() {
        return logError;
    }

    public static void setLogError(boolean logError) {
        Settings.logError = logError;
    }

    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public static void setLoggingEnabled(boolean loggingEnabled) {
        Settings.loggingEnabled = loggingEnabled;
    }

    public static boolean isPinCodesEnabled() {
        return pinCodesEnabled;
    }

    public static void setPinCodesEnabled(boolean pinCodesEnabled) {
        Settings.pinCodesEnabled = pinCodesEnabled;
    }
}
