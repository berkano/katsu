package ld33.entities;

import katsu.K;
import katsu.KDirection;

/**
 * Created by shaun on 22/08/2015.
 */
public class NPC extends MobBase {

    private long didLastPathFind;
    private boolean hasDoneFirstPathFind = false;

    public boolean isHasDoneFirstPathFind() {
        return hasDoneFirstPathFind;
    }

    public void setHasDoneFirstPathFind(boolean hasDoneFirstPathFind) {
        this.hasDoneFirstPathFind = hasDoneFirstPathFind;
    }

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
            // Don't start moving randomly until we have at least been in range of the player
            if (hasDoneFirstPathFind) {
                moveRequested(KDirection.random());
            }
        }

    }

}
