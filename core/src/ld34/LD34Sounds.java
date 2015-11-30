package ld34;

import com.badlogic.gdx.audio.Sound;
import katsu.K;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD34Sounds {

    public static boolean musicPlaying = false;

    public static void stopAllMusic() {
//        music.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        stopAllMusic();
//        music.loop();
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
