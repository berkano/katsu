package ld32;

import com.badlogic.gdx.audio.Sound;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD32Sounds {

    public static Sound mole_move = KResource.loadSound("mole-move.wav");
    public static Sound mole_dig = KResource.loadSound("mole-dig.wav");
    public static Sound mole_die = KResource.loadSound("mole-die.wav");
    public static Sound game_restart = KResource.loadSound("game-restart.wav");
    public static Sound music1 = KResource.loadSound("music1.mp3");
    public static Sound poop_explode = KResource.loadSound("poop-explode.wav");
    public static Sound complete_level = KResource.loadSound("newsounds-complete-level.wav");
    public static Sound eat_worm = KResource.loadSound("newsounds-eatworm.wav");
    public static Sound kill_enemy = KResource.loadSound("newsounds-kill-enemy.wav");
    public static Sound poop = KResource.loadSound("newsounds-poop.wav");
    public static Sound win_game = KResource.loadSound("newsounds-wingame.wav");
    public static Sound waypoint = KResource.loadSound("waypoint.wav");
    public static Sound hurt = KResource.loadSound("hurt.wav");
    public static Sound winMusic = KResource.loadSound("ld32-music-win.mp3");

    public static boolean musicPlaying = false;

    public static void stopAllMusic() {
        music1.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        stopAllMusic();
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
