package katsu.test.troll;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import katsu.K;

/**
 * Created by shaun on 21/01/2018.
 */
public class TrollCastleSounds {

    // TODO use an enum approach
    public Music currentMusic;
    public Music normalMusic;
    public Music psychMusic;

    public Sound talk1;
    public Sound talk2;
    public Sound talk3;
    public Sound talk4;
    public Sound walk;
    public Sound select1;
    public Sound mine;
    public Sound goldSound;
    public Sound die;
    public Sound mystery;
    public Sound rocks;
    public Sound water;
    public Sound grow;

    Sound psych;
    Sound build;
    Sound fish;
    Sound plant;

    void setupSounds() {

        talk1 = K.resource.loadSound("troll-talk-1.ogg");
        talk2 = K.resource.loadSound("troll-talk-2.ogg");
        talk3 = K.resource.loadSound("troll-talk-3.ogg");
        talk4 = K.resource.loadSound("troll-talk-4.ogg");
        mine = K.resource.loadSound("mine.ogg");
        psych = K.resource.loadSound("psych.ogg");
        walk = K.resource.loadSound("walk.ogg");
        normalMusic = K.resource.loadMusic("normalMusic.ogg");
        psychMusic = K.resource.loadMusic("psychMusic.ogg");
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

        normalMusic.setLooping(true);
        normalMusic.setVolume(0.85f);

        psychMusic.setLooping(true);
        psychMusic.setVolume(0.25f);

        currentMusic = normalMusic;

        if (TrollDevFlags.playMusicOnStart) {
            currentMusic.play();
        }

    }

}
