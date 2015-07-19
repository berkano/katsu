package ld32;

import com.badlogic.gdx.Input;
import katsu.KSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Settings extends KSettings {

    @Override
    public boolean isLogFPS() {
        return isDevMode();
    }

    @Override
    public String getGameName() {
        return "Noel and the Spiders";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "LD32";
    }

    public boolean startWithMusic = !isDevMode();
    public boolean startWithPausedHelp = !isDevMode();
    public int maxLives = 5;
    public int maxPoop = 8;
    public int startLives = isDevMode() ? maxLives : maxLives;
    public int startLevel = isDevMode() ? 1 : 1;
    public int startPoop = isDevMode() ? 0 : 0;
    public boolean teleportToSpecialPosition = false;

    public boolean nerfLava = false;
    public long enemyPathFindInterval = 500;
    public int enemyPathFindingDistance = 8;
    public boolean displayPathFindingHints = false;
    private static LD32Settings _instance;

    @Override
    public int getPauseKey() {
        return Input.Keys.ENTER;
    }

    public static LD32Settings get() {
        if (_instance == null) {
            _instance = new LD32Settings();
        }
        return _instance;
    }
}
