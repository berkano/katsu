package ld32.entities;

import katsu.K;
import katsu.KDirection;
import katsu.KEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Spider extends Mob {

    private boolean hasDoneFirstPathFind = false;
    private long didLastPathFind = K.currentTime();

    public Spider() {
        super();
        this.setMaxMoveInterval(500);
    }

    @Override
    public void update() {
        super.update();
        if (getPathFinderNextDirection() != null) {
            didLastPathFind = K.currentTime();
            moveRequested(getPathFinderNextDirection());
            hasDoneFirstPathFind = true;
            setPathFinderNextDirection(null);
        } else {
            if (hasDoneFirstPathFind) {
                // Don't move randomly if we have tried path finding recently
                if (didLastPathFind < K.currentTime() - 2000) {
                    moveRequested(KDirection.random());
                }
            }
        }
    }

    @Override
    public void onCollide(KEntity other) {
        super.onCollide(other);
        if (other instanceof Mole) {
            Mole mole = (Mole) other;
            mole.tryLoseLife();
        }
    }
}
