package mini73;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 26/04/13
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class Sounds {

    public static Sound select = Util.loadSound("bleep.wav");
    public static Sound transport = Util.loadSound("transport.wav");
    public static Music mus1 = Util.loadMusic("mus1.ogg");
    public static Music mus2 = Util.loadMusic("mus2.ogg");
    public static Music mus3 = Util.loadMusic("mus3.ogg");
    public static Music win = Util.loadMusic("win.ogg");
    public static Sound engine = Util.loadSound("engine.wav");
    public static Sound hurt = Util.loadSound("hurt.wav");
    public static Sound robotspeak = Util.loadSound("robot-speak.wav");
    public static Sound shipspeak = Util.loadSound("ship-speak.wav");
    public static Sound sheep = Util.loadSound("sheep.wav");
    public static Sound friendlyspeak = Util.loadSound("friendly-person-speak.wav");
    public static Sound friendlyshipspeak = Util.loadSound("friendly-ship-speak.wav");
    public static Sound enemyshipspeak = Util.loadSound("enemy-ship-speak.wav");
    public static Sound enemypersonspeak = Util.loadSound("enemy-person-speak.wav");

    public static void stopAllMusic() {
        Sounds sounds = Katsu.game.sounds;
        Util.stopAll(sounds.mus1, sounds.mus2, sounds.mus3, sounds.win);
    }

    public static long nextAmbientMusic = chooseNextMusicPlayTime();

    public static long chooseNextMusicPlayTime() {
        return System.currentTimeMillis() + Katsu.random.nextInt(180000) + 60000;
    }

    public static void playAmbientMusicRandomly() {
        if (nextAmbientMusic < System.currentTimeMillis()) {
            stopAllMusic();
            Util.playOneOf(mus1, mus2, mus3);
            nextAmbientMusic = chooseNextMusicPlayTime();
        }
    }
}
