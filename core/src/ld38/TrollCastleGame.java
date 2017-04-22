package ld38;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleGame extends KGame {

    private static TrollCastleGame _instance;

    public TrollCastleUI ui;

    public ArrayList<KRoom> rooms;

    public Music music1;
    public Sound talk1;
    public Sound select1;

    public List<String> waffle;
    public int xp = 0;
    public int trolls = 0;
    public int gold = 0;
    public int complete = 0;
    public boolean hasEatenMushroom = false;
    public boolean hasMined = false;


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
        ui.bottomBar.writeLine("Welcome to Troll Castle! [CYAN]WORK IN PROGRESS");
//        ui.bottomBar.writeLine("[GRAY]You can click on things and drag the map. That's it");

        loadWaffle();
        setupSounds();

    }

    private void loadWaffle() {
        waffle = new ArrayList<>();
        String[] waffles = K.resource.loadText("random-troll-waffle.txt").split("\\r?\\n");
        for (String w : waffles) {
            waffle.add(w);
        }
    }

    private void setupSounds() {

            talk1 = K.resource.loadSound("troll-talk-1.ogg");
            music1 = K.resource.loadMusic("troll1.mp3");
            select1 = K.resource.loadSound("beep-select.ogg");
            music1.setLooping(true);
            music1.setVolume(0.25f);

            if (DevHelper.playMusicOnStart) {
                music1.play();
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

    @Override
    public void update() {
        super.update();
        ui.topBar.clear();
        calculateCompleted();
        String welldone = "";
        if (complete >= 100) {
            welldone = " [GREEN]*** WELL DONE!!! ***";
        }
        ui.topBar.writeLine(trolls +" [GRAY]trolls :: [GREEN]" + xp + "[GRAY] XP :: [YELLOW]" + gold + "[GRAY] Gold :: [CYAN]" + complete + "[GRAY]% complete" + welldone);
    }

    private void calculateCompleted() {
        int oldCompleted = complete;
        complete = 0;
        if (hasEatenMushroom) complete += 50;
        if (hasMined) complete += 50;
        if (complete > oldCompleted) {
            int diff = complete - oldCompleted;
            ui.bottomBar.writeLine("[GREEN]+[CYAN]"+diff+"[WHITE]% completed!");
        }
    }

    public static TrollCastleGame instance() {
        if (_instance == null) {
            throw new RuntimeException("Game instance accessed before creation");
        }
        return _instance;
    }
}
