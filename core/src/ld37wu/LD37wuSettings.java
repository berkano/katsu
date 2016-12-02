package ld37wu;

import com.badlogic.gdx.Input;
import katsu.KSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD37wuSettings extends KSettings {

    public static int fireChance = get().isDevMode() ? 30 : 30; // chance 1 in X every 1 second
    public final boolean startPaused = !isDevMode();
    public final long enemyPathFindInterval = 1000;

    @Override
    public int getToggleMusicKey() {
        return Input.Keys.U;
    }

    public final int enemyPathFindingDistance = 4;

    @Override
    public boolean isFullScreenBorderless() {
        return false;
    }

    @Override
    public int getHres() {
        return 1024;
    }

    @Override
    public int getVres() {
        return 768;
    }

    @Override
    public boolean isLogFPS() {
        return isDevMode();
    }

    @Override
    public String getGameName() {
        return "growing";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "ld34";
    }

    @Override
    public int getPauseKey() {
        return Input.Keys.SPACE;
    }

    public final boolean startWithMusic = !isDevMode();
    public final boolean startWithPausedHelp = !isDevMode();

    private static LD37wuSettings _instance;

    public static LD37wuSettings get() {
        if (_instance == null) {
            _instance = new LD37wuSettings();
        }
        return _instance;
    }
}
