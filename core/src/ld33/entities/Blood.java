package ld33.entities;

import katsu.K;
import katsu.KEntity;

/**
 * Created by shaun on 23/08/2015.
 */
public class Blood extends KEntity {

    private final long createdTime = System.currentTimeMillis();

    public Blood() {
        this.setSolid(false);
        this.setZLayer(1000);
        this.setSpriteRotation(K.random.nextInt(360));
    }

    @Override
    public void update() {
        super.update();
        long age = System.currentTimeMillis() - createdTime;
        if (age > 3000) setZLayer(1);
        if (age > 10000) {
            destroy();
        }
    }
}
