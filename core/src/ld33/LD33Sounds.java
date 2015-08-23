package ld33;

import com.badlogic.gdx.audio.Sound;
import katsu.K;
import katsu.KResource;

/**
 * Created by shaun on 18/04/2015.
 */
public class LD33Sounds {

    public static Sound combat1 = KResource.loadSound("combat1.wav");
    public static Sound combat2 = KResource.loadSound("combat2.wav");
    public static Sound combat3 = KResource.loadSound("combat3.wav");
    public static Sound combat4 = KResource.loadSound("combat4.wav");

    public static Sound death = KResource.loadSound("death.wav");
    public static Sound hello_female = KResource.loadSound("hello_female.wav");
    public static Sound hello_male = KResource.loadSound("hello_male.wav");
    public static Sound meow = KResource.loadSound("meow.wav");
    public static Sound restart = KResource.loadSound("restart.wav");
    public static Sound sheep = KResource.loadSound("sheep.wav");
    public static Sound transform = KResource.loadSound("transform.wav");

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

    public static void randomHello() {
        if (K.random.nextBoolean()) {
            hello_female.play();
        } else {
            hello_male.play();
        }
    }
}
