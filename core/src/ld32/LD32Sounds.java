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
//    public static Sound mole_eat = PankoResource.loadSound("mole-eat.wav");
    public static Sound mole_die = PankoResource.loadSound("mole-die.wav");
    public static Sound game_restart = PankoResource.loadSound("game-restart.wav");
    public static Sound music1 = PankoResource.loadSound("music1.mp3");
    public static Sound poop_explode = PankoResource.loadSound("poop-explode.wav");
//    public static Sound mole_poop = PankoResource.loadSound("mole-poop.wav");
    public static Sound complete_level = PankoResource.loadSound("newsounds-complete-level.wav");
    public static Sound eat_worm = PankoResource.loadSound("newsounds-eatworm.wav");
    public static Sound kill_enemy = PankoResource.loadSound("newsounds-kill-enemy.wav");
    public static Sound poop = PankoResource.loadSound("newsounds-poop.wav");
    public static Sound win_game = PankoResource.loadSound("newsounds-wingame.wav");
    public static Sound waypoint = PankoResource.loadSound("waypoint.wav");
    public static Sound hurt = PankoResource.loadSound("hurt.wav");

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
