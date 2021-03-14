package katsu.test.troll.entities;

import com.badlogic.gdx.graphics.Color;
import katsu.K;
import katsu.test.troll.TrollCastleSounds;
import katsu.test.troll.TrollDevFlags;
import katsu.test.troll.TrollCastleGame;

/**
 * Created by shaun on 27/04/2017.
 */
public class TrollMind {

    private boolean hasHadPsychedelics = TrollDevFlags.allTrollsPsychedOnStart;
    private boolean psychedelic = false;
    private long lastPsychMillls = System.currentTimeMillis();
    private long startPyschMillis = 0;
    private Troll host;
    private TrollCastleGame game;
    private TrollCastleSounds sounds;

    public void setPsychedelic(boolean psychedelic) {
        this.psychedelic = psychedelic;
        if (psychedelic) {
            startPyschMillis = System.currentTimeMillis();
            hasHadPsychedelics = true;
            sounds.currentMusic.pause();
            sounds.currentMusic = sounds.psychMusic;
            sounds.currentMusic.play();
        }
    }

    TrollMind(Troll host, TrollCastleGame game) {
        this.host = host;
        this.game = game;
        this.sounds = game.sounds;
    }

    public boolean hasHadPsychedelics() {
        return hasHadPsychedelics;
    }

    void updatePsychedelics() {

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
        sounds.currentMusic = sounds.normalMusic;
        sounds.currentMusic.play();
        host.getAppearance().setSpriteColour(Color.WHITE);
        // add a swimtube if not already got one
        host.addSwimTube();

    }

    boolean isPsychedelic() {
        return psychedelic;
    }
}
