package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import panko.Panko;
import panko.PankoDirection;
import panko.PankoEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Spider extends Mob {

    private boolean hasDoneFirstPathFind = false;
    private long didLastPathFind = Panko.currentTime();

    public Spider() {
        super();
        this.setMaxMoveInterval(500);
    }

    @Override
    public void update() {
        super.update();
        if (getPathFinderNextDirection() != null) {
//            Panko.getUI().writeText("Spider trying to move "+getPathFinderNextDirection() + " due to path finding");
            didLastPathFind = Panko.currentTime();
            moveRequested(getPathFinderNextDirection());
            hasDoneFirstPathFind = true;
            setPathFinderNextDirection(null);
        } else {
            if (hasDoneFirstPathFind) {
                // Don't move randomly if we have tried path finding recently
                if (didLastPathFind < Panko.currentTime() - 2000) {
                    moveRequested(PankoDirection.random());
                }
            }
        }
    }

    @Override
    public void onCollide(PankoEntity other) {
        super.onCollide(other);
        if (other instanceof Mole) {
            Mole mole = (Mole) other;
            mole.tryLoseLife();
        }
    }
}
