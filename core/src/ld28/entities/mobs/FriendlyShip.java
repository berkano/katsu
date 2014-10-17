package ld28.entities.mobs;

import ld28.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class FriendlyShip extends FriendlyMob {
    public FriendlyShip() {
        super();
        this.orientSpriteByMovement = true;

    }

    @Override
    public void afterMoved() {
        super.afterMoved();


    }
}
