package ld33;

import com.badlogic.gdx.Input;
import katsu.KSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD33Settings extends KSettings {

    public boolean startPaused = !isDevMode();

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
