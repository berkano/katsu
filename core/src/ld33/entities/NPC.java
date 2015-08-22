package ld33.entities;

import katsu.KDirection;

/**
 * Created by shaun on 22/08/2015.
 */
public class NPC extends Mob {

    public NPC()
    {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setzLayer(10);
        this.setUpdateAsRogueLike(true);
    }

    @Override
    public void update() {
        super.update();
        moveRequested(KDirection.random());
    }

}
