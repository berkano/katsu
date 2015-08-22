package ld33.entities;

import katsu.K;
import katsu.KDirection;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Monster extends KEntityBase {

    public Monster()
    {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setMaxMoveInterval(50);
    }

    @Override
    public void render() {
        lookAtMe();
        super.render();
    }

    @Override
    public boolean moveRequested(KDirection direction) {
        boolean result = super.moveRequested(direction);
        lookAtMe();
        return result;
    }
}
