package ld33.entities;

import katsu.K;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class FloorBase extends KEntityBase {

    public FloorBase() {
        super();
        this.setzLayer(-100);
        this.setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(15) - 7);
        this.setSpriteScale(K.random.nextFloat() * 0.2f + 1.2f);
    }
}
