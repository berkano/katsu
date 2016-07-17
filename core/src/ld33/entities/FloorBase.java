package ld33.entities;

import katsu.K;
import katsu.KEntity;

/**
 * Created by shaun on 22/08/2015.
 */
public class FloorBase extends KEntity {

    public FloorBase() {
        super();
        this.getAppearance().setZLayer(-100);
        this.getAppearance().setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(15) - 7);
        this.getAppearance().setSpriteScale(K.random.nextFloat() * 0.2f + 1.2f);
    }
}
