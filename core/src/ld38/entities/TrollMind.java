package ld38.entities;

import com.badlogic.gdx.graphics.Color;
import katsu.K;
import ld38.TrollCastleSounds;
import ld38.TrollDevFlags;
import ld38.TrollCastleGame;

/**
 * Created by shaun on 27/04/2017.
 */
public class TrollMind {

    boolean hasHadPsychedelics = TrollDevFlags.allTrollsPsychedOnStart;
    boolean psychedelic = false;
    long lastPsychMillls = System.currentTimeMillis();
    long startPyschMillis = 0;
    Troll host;
    TrollCastleGame game;
    TrollCastleSounds sounds;

    public void setPsychedelic(boolean psychedelic) {
        this.psychedelic = psychedelic;
        if (psychedelic == true) {
            startPyschMillis = System.currentTimeMillis();
            hasHadPsychedelics = true;
            sounds.currentMusic.pause();
            sounds.currentMusic = sounds.music3;
            sounds.currentMusic.play();
        }
    }

    public TrollMind(Troll host, TrollCastleGame game) {
        this.host = host;
        this.game = game;
        this.sounds = game.sounds;
    }

    public boolean hasHadPsychedelics() {
        return hasHadPsychedelics;
    }

    public void updatePsychedelics() {

        if (!psychedelic) return;

        if (startPyschMillis < System.currentTimeMillis() - 30000) {
            endPsychedelics();
        }

        if (lastPsychMillls < System.currentTimeMillis() - 50) {
            lastPsychMillls = System.currentTimeMillis();
            displayPsychedelicSymptoms();
        }

    }

    private void displayPsychedelicSymptoms() {

        int nextC = K.random.nextInt(6);
        if (nextC == 0) host.getAppearance().setSpriteColour(Color.PURPLE);
        if (nextC == 1) host.getAppearance().setSpriteColour(Color.LIME);
        if (nextC == 2) host.getAppearance().setSpriteColour(Color.CYAN);
        if (nextC == 3) host.getAppearance().setSpriteColour(Color.GOLD);
        if (nextC == 4) host.getAppearance().setSpriteColour(Color.PINK);
        if (nextC == 5) host.getAppearance().setSpriteColour(Color.RED);

        if (K.random.nextInt(100) == 3){
            int w = K.random.nextInt(game.waffle.size());
            host.say(game.waffle.get(w));
        }

    }

    private void endPsychedelics() {

        psychedelic = false;
        sounds.currentMusic.pause();
        sounds.currentMusic = sounds.music2;
        sounds.currentMusic.play();
        host.getAppearance().setSpriteColour(Color.WHITE);
        // add a swimtube if not already got one
        host.addSwimTube();

    }

    public boolean isPsychedelic() {
        return psychedelic;
    }
}
