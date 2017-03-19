package mini73;

import com.badlogic.gdx.Input;
import katsu.KSettings;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mini73Settings extends KSettings {

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
        return "singleton";
    }

    @Override
    public String getGameAuthor() {
        return "berkano";
    }

    @Override
    public String getGameDescription() {
        return "MiniLD 73";
    }

    @Override
    public int getPauseKey() {
        return Input.Keys.SPACE;
    }

    public final boolean startWithMusic = !isDevMode();
    public final boolean startWithPausedHelp = !isDevMode();

    private static Mini73Settings _instance;

    public static Mini73Settings get() {
        if (_instance == null) {
            _instance = new Mini73Settings();
        }
        return _instance;
    }
}
