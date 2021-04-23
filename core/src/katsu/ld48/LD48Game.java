package katsu.ld48;

import katsu.K;
import katsu.game.KGame;
import katsu.model.KRoom;
import katsu.resources.KSettings;
import katsu.test.troll.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class LD48Game extends KGame {

    private ArrayList<KRoom> rooms;
    private boolean hasCompleted = false;
    private static LD48Game _instance;
    public LD48UI ui;
    public LD48Sounds sounds = new LD48Sounds();

    public LD48Game() {
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
        ui = new LD48UI();
        ui.start();
        if (!LD48DevFlags.showHelpOnStart) {
            ui.toggleHelp();
        }
        sounds.setupSounds();

    }

    @Override
    public ArrayList<KRoom> getRooms() {
        if (rooms == null) {
            rooms = new ArrayList<>();
            rooms.add(new LD48Map());
        }
        return rooms;
    }

    @Override
    public String getResourceRoot() {
        return "katsu/ld48";
    }

    @Override
    public KSettings buildSettings() {
        return new LD48Settings();
    }

    @Override
    public void update() {
        super.update();
        ui.topBar.clear();
        ui.topBar.writeLine("LD48");
    }

    public static LD48Game instance() {
        if (_instance == null) {
            throw new RuntimeException("Game instance accessed before creation");
        }
        return _instance;
    }
}