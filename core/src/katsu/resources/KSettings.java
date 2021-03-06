package katsu.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by shaun on 16/11/2014.
 */
public class KSettings {

    @Getter @Setter private int hres = 1024;
    @Getter @Setter private int vres = 768;
    @Getter @Setter private int targetFrameRate = 30;
    @Getter @Setter private boolean vsync = false;
    @Getter @Setter private int gridSize = 16;
    @Getter @Setter private boolean logFPS = false;
    @Getter @Setter private boolean devMode = getDevModeFromSystemProps();
    @Getter @Setter private boolean logging = devMode;
    @Getter @Setter private boolean logPathfinding = false;
    @Getter @Setter private boolean fullScreenBorderless = false;
    @Getter @Setter private int pauseKey = Input.Keys.P;
    @Getter @Setter private int toggleMusicKey = Input.Keys.M;
    @Getter @Setter private String gameName = "Game Name";
    @Getter @Setter private String gameAuthor = "Author";
    @Getter @Setter private String gameDescription = "Description";
    @Getter @Setter private int windowWidth;
    @Getter @Setter private int windowHeight;

    private boolean getDevModeFromSystemProps() {
        return Boolean.parseBoolean(System.getProperty("devMode", "false"));
    }

    public void toggleFullScreen() {
        if (Gdx.graphics.isFullscreen()) {
            Gdx.graphics.setWindowedMode(getHres(), getVres());
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
    }


    public boolean isProduction() {
        return !isDevMode();
    }
}
