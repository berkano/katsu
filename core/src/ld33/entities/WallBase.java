package ld33.entities;

import katsu.K;
import katsu.KEntity;

/**
 * Created by shaun on 22/08/2015.
 */
public class WallBase extends KEntity {

    public WallBase() {
        super();
        this.setSolid(true);
        this.getAppearance().setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(6) - 3);
        this.getAppearance().setSpriteScale(K.random.nextFloat() * 0.1f + 1.0f);

    }
}
