package ld33.entities;

import katsu.K;
import katsu.KEntity;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Mob extends KEntityBase {

    public Mob() {
        super();
        this.setFlipSpriteOnMove(true);
    }

    public boolean isEnemy(KEntity other) {

        if (!(this instanceof Monster)) {
            if (other instanceof Monster) return true;
        }

        if ((this instanceof Monster)) {
            if (other instanceof Mob) {
                if (!(other instanceof Monster)) return true;
            }
        }

        return false;
    }

    @Override
    public void onCollide(KEntity other) {
        super.onCollide(other);
        if (isEnemy(other)) {
            String thisStr = this.getClass().getSimpleName();
            String thatStr = other.getClass().getSimpleName();
            K.getUI().writeText(thisStr + " attacks " + thatStr + "!");
        }
    }
}
