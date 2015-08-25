package ld33.entities;

import katsu.K;
import katsu.KEntityBase;

/**
 * Created by shaun on 23/08/2015.
 */
public class Blood extends KEntityBase {

    private final long createdTime = System.currentTimeMillis();

    public Blood() {
        this.setSolid(false);
        this.setzLayer(1000);
        this.setSpriteRotation(K.random.nextInt(360));
    }

    @Override
    public void update() {
        super.update();
        long age = System.currentTimeMillis() - createdTime;
        if (age > 3000) setzLayer(1);
        if (age > 10000) {
            destroy();
        }
    }
}
