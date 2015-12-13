package katsu;

import com.badlogic.gdx.Input;

/**
 * Created by shaun on 16/11/2014.
 */
public class KSettings {

    private int hres = 1024;
    private int vres = 768;
    private int targetFrameRate = 30;
    private boolean vsync = false;
    private int gridSize = 16;
    private boolean logFPS = false;
    private boolean devMode = getDevModeFromSystemProps();
    private boolean logging = devMode;

    public boolean isFullScreenBorderless() {
        return fullScreenBorderless;
    }

    public void setFullScreenBorderless(boolean fullScreenBorderless) {
        this.fullScreenBorderless = fullScreenBorderless;
    }

    private boolean fullScreenBorderless = false;

    private boolean getDevModeFromSystemProps() {
        return Boolean.parseBoolean(System.getProperty("devMode", "false"));
    }

    private int pauseKey = Input.Keys.P;

    public int getHres() {
        return hres;
    }

    public void setHres(int hres) {
        this.hres = hres;
    }

    public int getVres() {
        return vres;
    }

    public void setVres(int vres) {
        this.vres = vres;
    }

    public int getTargetFrameRate() {
        return targetFrameRate;
    }

    public void setTargetFrameRate(int targetFrameRate) {
        this.targetFrameRate = targetFrameRate;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public String getGameName() {
        return "Game Name";
    }

    public String getGameAuthor() {
        return "Author";
    }

    public String getGameDescription() {
        return "Description";
    }

    public int getPauseKey() {
        return pauseKey;
    }

    public void setPauseKey(int pauseKey) {
        this.pauseKey = pauseKey;
    }

    public boolean isLogFPS() {
        return logFPS;
    }

    public void setLogFPS(boolean logFPS) {
        this.logFPS = logFPS;
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    public int getToggleMusicKey() {
        return Input.Keys.M;
    }

    public boolean isLogging() {
        return logging;
    }

    public void setLogging(boolean logging) {
        this.logging = logging;
    }
}
