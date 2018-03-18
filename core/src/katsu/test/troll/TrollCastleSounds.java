package katsu.test.troll;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.K;

/**
 * Created by shaun on 21/01/2018.
 */
public class TrollCastleSounds {

    public Music currentMusic;
    public Music music1;
    public Music music2;
    public Music music3;
    public Sound talk1;
    public Sound talk2;
    public Sound talk3;
    public Sound talk4;
    public Sound walk;
    public Sound select1;
    public Sound psych;
    public Sound mine;
    public Sound goldSound;
    public Sound die;
    public Sound mystery;
    public Sound build;
    public Sound rocks;
    public Sound fish;
    public Sound water;
    public Sound plant;
    public Sound grow;

    protected void setupSounds() {

        talk1 = K.resource.loadSound("troll-talk-1.ogg");
        talk2 = K.resource.loadSound("troll-talk-2.ogg");
        talk3 = K.resource.loadSound("troll-talk-3.ogg");
        talk4 = K.resource.loadSound("troll-talk-4.ogg");
        mine = K.resource.loadSound("mine.ogg");
        psych = K.resource.loadSound("psych.ogg");
        walk = K.resource.loadSound("walk.ogg");
        music1 = K.resource.loadMusic("music1.ogg");
        music2 = K.resource.loadMusic("music2.ogg");
        music3 = K.resource.loadMusic("music3.ogg");
        select1 = K.resource.loadSound("beep-select.ogg");
        goldSound = K.resource.loadSound("gold.ogg");
        die = K.resource.loadSound("die.ogg");
        mystery = K.resource.loadSound("mystery.ogg");
        rocks = K.resource.loadSound("rocks.ogg");
        build = K.resource.loadSound("build.ogg");
        fish = K.resource.loadSound("fish.ogg");
        water = K.resource.loadSound("water.ogg");
        plant = K.resource.loadSound("plant.ogg");
        grow = K.resource.loadSound("grow.ogg");

        music1.setLooping(true);
        music1.setVolume(0.5f);
        music2.setLooping(true);
        music2.setVolume(0.85f);
        music3.setLooping(true);
        music3.setVolume(0.25f);

        currentMusic = music2;

        if (TrollDevFlags.playMusicOnStart) {
            currentMusic.play();
        }

    }

}
