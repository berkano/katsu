package ld33.entities;

import katsu.KDirection;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Warrior extends Mob {

    public Warrior()
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
