package mini55;

import katsu.KatsuGame;
import katsu.LevelManager;
import katsu.Room;
import katsu.Settings;

/**
 * Created by shaun on 19/10/2014.
 */
public class KatsuGameImpl implements KatsuGame {

    private LevelManagerImpl levelManager = new LevelManagerImpl();
    private Mini55Settings settings = new Mini55Settings();

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
        return "mini55";
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
}
