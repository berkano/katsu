package starter;

import katsu.KatsuGame;
import katsu.LevelManager;
import katsu.Room;
import katsu.Settings;

/**
 * Created by shaun on 19/10/2014.
 */
public class KatsuGameImpl implements KatsuGame {

    private LevelManagerImpl levelManager = new LevelManagerImpl();
    private StarterSettings settings = new StarterSettings();

    @Override
    public LevelManager getLevelManager() {
        return levelManager;
    }

    @Override
    public int getNumHelpPages() {
        return 0;
    }

    @Override
    public void stopAllMusic() {
        Sounds.stopAllMusic();
    }

    @Override
    public Room createRoomForTmx(String tmx) {
        return new MainRoom(tmx);
    }

    @Override
    public String getResourceRoot() {
        return "starter";
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
}