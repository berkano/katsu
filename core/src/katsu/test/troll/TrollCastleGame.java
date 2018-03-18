package katsu.test.troll;

import katsu.*;
import katsu.game.KGame;
import katsu.model.KRoom;
import katsu.resources.KSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleGame extends KGame {

    private static TrollCastleGame _instance;

    public TrollCastleUI ui;

    public ArrayList<KRoom> rooms;

    public List<String> waffle;
    public int trolls = 0;
    public int gold = 0;
    public int stone = 0;
    public int complete = 0;

    public int wallsBuilt = 0;
    public int towersBuilt = 0;
    public int goldTowersBuilt = 0;

    public boolean hasEatenMushroom = false;

    public TrollCastleSounds sounds = new TrollCastleSounds();

    boolean hasCompleted = false;

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
        if (!TrollDevFlags.showHelpOnStart) {
            ui.toggleHelp();
        }
        loadWaffle();
        sounds.setupSounds();

        if (TrollDevFlags.cheatResources) {
            gold = 1000;
            stone = 1000;
        }

    }

    private void loadWaffle() {
        waffle = new ArrayList<>();
        String[] waffles = K.resource.loadText("random-troll-waffle.txt").split("\\r?\\n");
        for (String w : waffles) {
            waffle.add(w);
        }
    }

    @Override
    public ArrayList<KRoom> getRooms() {

        if (rooms == null) {
            rooms = new ArrayList<>();
            rooms.add(new TrollMap());
        }
        return rooms;
    }

    @Override
    public String getResourceRoot() {
        return "test/troll";
    }

    @Override
    public KSettings buildSettings() {
        return new TrollCastleSettings();
    }

    @Override
    public void update() {
        super.update();
        ui.topBar.clear();
        String welldone = "";
        if (complete >= 100) {
            welldone = " [GREEN]*** WELL DONE!!! ***";
        }
        ui.topBar.writeLine("[GREEN]  " + trolls +" [GRAY]trolls    [YELLOW]" + gold + "[GRAY] Gold    [WHITE]" + stone + "[GRAY] Stone    "+completionStatus());
    }

    private String completionStatus() {

        if (trolls == 0) {
            return ("[RED]Game Over - All Trolls Died :-(");
        }

        String result = "[GRAY]Castle: [MAGENTA]";
        if (wallsBuilt < 16) {
            result += wallsBuilt+"[GRAY]/16 walls built";
        } else {
            if (towersBuilt < 4) {
                result += towersBuilt+"[GRAY]/4 towers built";
            } else {
                if (goldTowersBuilt < 4) {
                    result += goldTowersBuilt + "[GRAY]/4 gold towers built";
                } else {
                    result += "[CYAN]COMPLETE! [RED]*[ORANGE]*[YELLOW]* [GREEN]Well Done! [YELLOW]*[ORANGE]*[RED]*";

                    if (!hasCompleted) {
                        // we just completed it.
                        ui.bottomBar.writeLine("[CYAN]Castle Complete! Well Done! Hope you enjoyed playing! [WHITE]~[ORANGE]berkano");
                    }

                    hasCompleted = true;
                }
            }
        }

        return result;

    }

    public static TrollCastleGame instance() {
        if (_instance == null) {
            throw new RuntimeException("Game instance accessed before creation");
        }
        return _instance;
    }
}