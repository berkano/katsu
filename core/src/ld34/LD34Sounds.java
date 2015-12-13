package ld34;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.K;
import katsu.KLog;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD34Sounds {

    public static boolean musicPlaying = false;
    public static Sound chop = KResource.loadSound("chop.wav");
    public static Sound walk = KResource.loadSound("walk.wav");
    public static Sound buy_land = KResource.loadSound("buy_land.wav");
    public static Sound plant = KResource.loadSound("plant.wav");
    public static Sound gone_wrong = KResource.loadSound("gone_wrong.wav");
    public static Sound music = KResource.loadSound("snowman-music.ogg");

    public static void stopAllMusic() {
        KLog.trace("stop music");
        music.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        KLog.trace("play music");
        stopAllMusic();
        music.loop();
        musicPlaying = true;
    }

    public static void toggleMusic() {
        KLog.trace("toggle music");
        if (!musicPlaying) {
            playMusic();
        } else {
            stopAllMusic();
        }
    }

}
