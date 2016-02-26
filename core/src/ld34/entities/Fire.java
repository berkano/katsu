package ld34.entities;

import katsu.K;
import katsu.KDirection;
import katsu.KLogger;
import ld34.LD34Sounds;

/**
 * Created by shaun on 12/12/2015.
 */
public class Fire extends LD34EntityBase {

    boolean playedSound = false;
    long lastTriedToSpreadFire = K.currentTime();

    public Fire() {
        setZLayer(5);
    }

    @Override
    public void update() {

        if (!playedSound) {
            LD34Sounds.fire.play();
            K.getUI().writeText("Oh noes! I think my trees are on fire!");
            playedSound = true;
        }

        if (K.random.nextInt(5) == 0) {
            if (K.random.nextBoolean()) {
                setSpriteFlip(true);
            } else {
                setSpriteFlip(false);
            }
        }

        spreadFire();

        super.update();
    }

    private void spreadFire() {

        if (lastTriedToSpreadFire < K.currentTime() - 7500) {

            lastTriedToSpreadFire = K.currentTime();

            // possible for fire to not have its room set yet and crash le world... blah
            if (getRoom() == null) {
                K.logger.warn("Fire had a null room when trying to spread");
                return;
            }

            for (KDirection direction : KDirection.values()) {

                Tree burnyFriend = (Tree)findFirstEntityOnGrid(Tree.class,
                        getGridX() + direction.dx(), getGridY() + direction.dy());

                if (burnyFriend != null) {
                    burnyFriend.setOnFire();
                }
            }

        }

    }
}
