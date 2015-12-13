package ld34.entities;

import katsu.K;
import katsu.KDirection;
import ld34.LD34Sounds;

/**
 * Created by shaun on 12/12/2015.
 */
public class Fire extends LD34EntityBase {

    boolean playedSound = false;

    public Fire() {
        setzLayer(5);
    }

    @Override
    public void update() {

        if (!playedSound) {
            LD34Sounds.fire.play();
            K.getUI().writeText("Oh no! I think my trees are on fire!");
            playedSound = true;
        }

        if (K.random.nextInt(5) == 0) {
            if (K.random.nextBoolean()) {
                setSpriteFlip(true);
            } else {
                setSpriteFlip(false);
            }
        }
        super.update();
    }
}
