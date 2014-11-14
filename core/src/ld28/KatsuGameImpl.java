package ld28;

import katsu.*;
import ld28.rooms.MainRoom;

/**
 * Created by shaun on 19/10/2014.
 */
public class KatsuGameImpl implements KatsuGame {

    private LevelManagerImpl levelManager = new LevelManagerImpl();
    private LD28Settings settings = new LD28Settings();

    @Override
    public LevelManager getLevelManager() {
        return levelManager;
    }

    @Override
    public int getNumHelpPages() {
        return Documentation.getNumHelpPages();
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
        return "ld28";
    }

    @Override
    public Settings getSettings() {
        return settings;
    }
}
