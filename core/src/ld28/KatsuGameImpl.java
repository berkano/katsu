package ld28;

import katsu.KatsuGame;
import katsu.LevelManager;
import katsu.Room;
import ld28.rooms.MainRoom;

/**
 * Created by shaun on 19/10/2014.
 */
public class KatsuGameImpl implements KatsuGame {

    private LevelManagerImpl levelManager = new LevelManagerImpl();

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
}
