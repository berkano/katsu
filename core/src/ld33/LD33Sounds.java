package ld33;

import com.badlogic.gdx.audio.Sound;
import katsu.K;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD33Sounds {

    public static final Sound combat1 = K.resources.loadSound("combat1.wav");
    public static final Sound combat2 = K.resources.loadSound("combat2.wav");
    public static final Sound combat3 = K.resources.loadSound("combat3.wav");
    public static final Sound combat4 = K.resources.loadSound("combat4.wav");

    public static final Sound death = K.resources.loadSound("death.wav");
    public static final Sound hello_female = K.resources.loadSound("hello_female.wav");
    public static final Sound hello_male = K.resources.loadSound("hello_male.wav");
    public static final Sound meow = K.resources.loadSound("meow.wav");
    public static final Sound restart = K.resources.loadSound("restart.wav");
    public static final Sound sheep = K.resources.loadSound("sheep.wav");
    public static final Sound transform = K.resources.loadSound("transform.wav");

    public static final Sound music = K.resources.loadSound("full-music.mp3");

    public static void combatSound() {
        int pick = K.random.nextInt(4);
        switch (pick) {
            case 0:
                combat1.play();
                break;
            case 1:
                combat2.play();
                break;
            case 2:
                combat3.play();
                break;
            case 3:
                combat4.play();
                break;
        }
    }


    public static boolean musicPlaying = false;

    public static void stopAllMusic() {
        music.stop();
        musicPlaying = false;
    }

    public static void playMusic() {
        stopAllMusic();
        music.loop();
        musicPlaying = true;
    }

    public static void toggleMusic() {
        if (!musicPlaying) {
            playMusic();
        } else {
            stopAllMusic();
        }
    }

    public static void randomHello() {
        if (K.random.nextBoolean()) {
            hello_female.play();
        } else {
            hello_male.play();
        }
    }
}
