package ld38;

import katsu.KGame;
import katsu.KRoom;
import katsu.KSettings;

import java.util.ArrayList;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleGame extends KGame {

    UI ui;

    ArrayList<KRoom> rooms;

    @Override
    public void render() {
        super.render();
        ui.render();
    }

    @Override
    public void start() {
        super.start();
        ui = new UI();
        ui.start();
        ui.topBar.writeLine("Troll Castle");
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
}
