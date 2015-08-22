package ld33.entities;

import katsu.K;
import katsu.KEntityBase;

/**
 * Created by shaun on 22/08/2015.
 */
public class Wall extends KEntityBase {

    public Wall() {
        super();
        this.setSolid(true);
        this.setSpriteRotation(K.random.nextInt(4) * 90 + K.random.nextInt(6) - 3);
        this.setSpriteScale(K.random.nextFloat() * 0.1f + 1.0f);

    }
}
