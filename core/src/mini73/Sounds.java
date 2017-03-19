package mini73;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.K;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 26/04/13
 * Time: 17:55
 * To change this template use File | Settings | File Templates.
 */
public class Sounds {

    public static Sound select = K.resource.loadSound("bleep.wav");
    public static Sound transport = K.resource.loadSound("transport.wav");
    public static Music mus1 = K.resource.loadMusic("mus1.ogg");
    public static Music mus2 = K.resource.loadMusic("mus2.ogg");
    public static Music mus3 = K.resource.loadMusic("mus3.ogg");
    public static Music win = K.resource.loadMusic("win.ogg");
    public static Sound engine = K.resource.loadSound("engine.wav");
    public static Sound hurt = K.resource.loadSound("hurt.wav");
    public static Sound robotspeak = K.resource.loadSound("robot-speak.wav");
    public static Sound shipspeak = K.resource.loadSound("ship-speak.wav");
    public static Sound sheep = K.resource.loadSound("sheep.wav");
    public static Sound friendlyspeak = K.resource.loadSound("friendly-person-speak.wav");
    public static Sound friendlyshipspeak = K.resource.loadSound("friendly-ship-speak.wav");
    public static Sound enemyshipspeak = K.resource.loadSound("enemy-ship-speak.wav");
    public static Sound enemypersonspeak = K.resource.loadSound("enemy-person-speak.wav");

    public static void stopAllMusic() {
        Util.stopAll(mus1, mus2, mus3, win);
    }

    public static long nextAmbientMusic = chooseNextMusicPlayTime();

    public static long chooseNextMusicPlayTime() {
        return System.currentTimeMillis() + K.random.nextInt(180000) + 60000;
    }

    public static void playAmbientMusicRandomly() {
        if (nextAmbientMusic < System.currentTimeMillis()) {
            stopAllMusic();
            Util.playOneOf(mus1, mus2, mus3);
            nextAmbientMusic = chooseNextMusicPlayTime();
        }
    }
}
