package ld34;

import com.badlogic.gdx.audio.Sound;
import katsu.K;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD34Sounds {

    public static boolean musicPlaying = false;
    public static Sound chop = K.resource.loadSound("chop.wav");
    public static Sound walk = K.resource.loadSound("walk.wav");
    public static Sound buy_land = K.resource.loadSound("buy_land.wav");
    public static Sound plant = K.resource.loadSound("plant.wav");
    public static Sound gone_wrong = K.resource.loadSound("gone_wrong.wav");
    public static Sound lightning = K.resource.loadSound("lightning.wav");
    public static Sound fire = K.resource.loadSound("fire.wav");
    public static Sound music = K.resource.loadSound("snowman-music.ogg");

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
