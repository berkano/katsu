package ld33;

import com.badlogic.gdx.Input;
import katsu.KSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD33Settings extends KSettings {

    public boolean startPaused = !isDevMode();
    public long enemyPathFindInterval = 1000;

    @Override
    public int getToggleMusicKey() {
        return Input.Keys.U;
    }

    public int enemyPathFindingDistance = 4;

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
        return "LD33 Game Title";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "LD33";
    }

    public boolean startWithMusic = !isDevMode();
    public boolean startWithPausedHelp = !isDevMode();

    private static LD33Settings _instance;

    public static LD33Settings get() {
        if (_instance == null) {
            _instance = new LD33Settings();
        }
        return _instance;
    }
}
