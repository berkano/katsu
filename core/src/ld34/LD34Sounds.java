package ld34;

import com.badlogic.gdx.audio.Sound;
import katsu.K;
import katsu.KLogger;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD34Sounds {

    public static boolean musicPlaying = false;
    public static Sound chop = K.resources.loadSound("chop.wav");
    public static Sound walk = K.resources.loadSound("walk.wav");
    public static Sound buy_land = K.resources.loadSound("buy_land.wav");
    public static Sound plant = K.resources.loadSound("plant.wav");
    public static Sound gone_wrong = K.resources.loadSound("gone_wrong.wav");
    public static Sound lightning = K.resources.loadSound("lightning.wav");
    public static Sound fire = K.resources.loadSound("fire.wav");
    public static Sound music = K.resources.loadSound("snowman-music.ogg");

    public static void stopAllMusic() {
        K.logger.trace("stop music");
        music.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        K.logger.trace("play music");
        stopAllMusic();
        music.loop();
        musicPlaying = true;
    }

    public static void toggleMusic() {
        K.logger.trace("toggle music");
        if (!musicPlaying) {
            playMusic();
        } else {
            stopAllMusic();
        }
    }

}
