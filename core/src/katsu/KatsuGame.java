package katsu;

/**
 * Created by shaun on 19/10/2014.
 */
public interface KatsuGame {
    LevelManager getLevelManager();

    int getNumHelpPages();

    void stopAllMusic();

    Room createRoomForTmx(String tmx);

    String getResourceRoot();

    Settings getSettings();
}
