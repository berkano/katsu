package ld31v2.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 06/12/2014.
 */
public class Skeleton extends PankoEntityBase {

    private long spawnTimeMillis;

    public Skeleton() {
        spawnTimeMillis = System.currentTimeMillis();
    }

    @Override
    public void update() {
        super.update();
        if (spawnTimeMillis < System.currentTimeMillis() - 10000) {
            Panko.queueRemoveEntity(this);
        }
    }
}
