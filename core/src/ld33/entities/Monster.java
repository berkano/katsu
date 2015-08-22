package ld33.entities;

import katsu.KDirection;

/**
 * Created by shaun on 22/08/2015.
 */
public class Monster extends MobBase {

    public Monster()
    {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setFlipSpriteOnMove(true);
        this.setMaxMoveInterval(75);
        this.setzLayer(100);
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
