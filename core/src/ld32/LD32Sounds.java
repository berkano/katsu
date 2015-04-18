package ld32;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import panko.PankoResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Sounds {

    public static Sound mole_move = PankoResource.loadSound("mole-move.wav");
    public static Sound mole_dig = PankoResource.loadSound("mole-dig.wav");
    public static Sound mole_eat = PankoResource.loadSound("mole-eat.wav");
    public static Sound mole_die = PankoResource.loadSound("mole-die.wav");
    public static Sound game_restart = PankoResource.loadSound("game-restart.wav");
    public static Sound music1 = PankoResource.loadSound("music1.mp3");
    public static boolean musicPlaying = false;

    public static void stopAllMusic() {
        music1.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        music1.loop();
        musicPlaying = true;
    }


    public static void toggleMusic() {
        if (!musicPlaying) {
            playMusic();
        } else {
            stopAllMusic();
        }
    }
}
