package ld38;

import com.badlogic.gdx.audio.Music;
import katsu.*;

import java.util.ArrayList;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleGame extends KGame {

    private static TrollCastleGame _instance;

    TrollCastleUI ui;

    ArrayList<KRoom> rooms;

    Music troll1;

    public TrollCastleGame() {
        super();
        _instance = this;
    }


    @Override
    public void render() {
        super.render();
        ui.render();
    }

    @Override
    public void start() {
        super.start();
        ui = new TrollCastleUI();
        ui.start();
        ui.topBar.writeLine("7 trolls :: 0 XP :: 0 Gold");
        ui.bottomBar.writeLine("Welcome to Troll Castle! [CYAN]No gameplay yet ;-)");

        loadAndStartMusic();

    }

    private void loadAndStartMusic() {

            troll1 = K.resource.loadMusic("troll1.mp3");
            troll1.setLooping(true);

            if (DevHelper.playMusicOnStart) {
                troll1.play();
            }

    }

    @Override
    public ArrayList<KRoom> getRooms() {

        if (rooms == null) {
            rooms = new ArrayList<>();
            rooms.add(new Map());
        }
        return rooms;
    }

    @Override
    public String getResourceRoot() {
        return "ld38";
    }

    @Override
    public void toggleMusic() {

    }

    @Override
    public KSettings buildSettings() {
        return new TrollCastleSettings();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public static TrollCastleGame instance() {
        if (_instance == null) {
            throw new RuntimeException("Game instance accessed before creation");
        }
        return _instance;
    }
}
