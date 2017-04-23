package ld38;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by shaun on 21/04/2017.
 */
public class TrollCastleGame extends KGame {

    private static TrollCastleGame _instance;

    public TrollCastleUI ui;

    public ArrayList<KRoom> rooms;

    public Music currentMusic;
    public Music music1;
    public Music music2;
    public Music music3;
    public Sound talk1;
    public Sound talk2;
    public Sound talk3;
    public Sound talk4;
    public Sound walk;
    public Sound select1;
    public Sound psych;
    public Sound mine;
    public Sound goldSound;
    public Sound die;
    public Sound mystery;
    public Sound build;
    public Sound rocks;

    public List<String> waffle;
    public int trolls = 0;
    public int gold = 0;
    public int stone = 0;
    public int complete = 0;

    public int wallsBuilt = 0;
    public int towersBuilt = 0;
    public int goldTowersBuilt = 0;

    public boolean hasEatenMushroom = false;
    public boolean hasMined = false;

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
        if (!DevHelper.showHelpOnStart) {
            ui.toggleHelp();
        }
        loadWaffle();
        setupSounds();

        if (DevHelper.cheatResources) {
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

    private void setupSounds() {

            talk1 = K.resource.loadSound("troll-talk-1.ogg");
            talk2 = K.resource.loadSound("troll-talk-2.ogg");
            talk3 = K.resource.loadSound("troll-talk-3.ogg");
            talk4 = K.resource.loadSound("troll-talk-4.ogg");
            mine = K.resource.loadSound("mine.ogg");
            psych = K.resource.loadSound("psych.ogg");
            walk = K.resource.loadSound("walk.ogg");
            music1 = K.resource.loadMusic("music1.ogg");
            music2 = K.resource.loadMusic("music2.ogg");
            music3 = K.resource.loadMusic("music3.ogg");
            select1 = K.resource.loadSound("beep-select.ogg");
            goldSound = K.resource.loadSound("gold.ogg");
            die = K.resource.loadSound("die.ogg");
            mystery = K.resource.loadSound("mystery.ogg");
            rocks = K.resource.loadSound("rocks.ogg");
            build = K.resource.loadSound("build.ogg");

            music1.setLooping(true);
            music1.setVolume(0.5f);
            music2.setLooping(true);
            music2.setVolume(0.85f);
            music3.setLooping(true);
            music3.setVolume(0.25f);

            currentMusic = music1;

            if (DevHelper.playMusicOnStart) {
                task(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        int loops = 0;
                        while (loops < 1000000) {
                            currentMusic = music1;
                            currentMusic.play();
                            Thread.sleep(60000);
                            currentMusic.pause();
                            currentMusic = music2;
                            currentMusic.play();
                            Thread.sleep(60000);
                            currentMusic.pause();
                            currentMusic = music3;
                            currentMusic.play();
                            Thread.sleep(60000);
                            currentMusic.pause();
                            loops++;
                        }
                        return true;
                    }
                });
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
