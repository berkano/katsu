package ld33.entities;

import katsu.K;
import katsu.KDirection;

/**
 * Created by shaun on 22/08/2015.
 */
public class NPC extends MobBase {

    private long didLastPathFind;
    private boolean hasDoneFirstPathFind = false;

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

}
