package ld33.entities;

import katsu.KEntity;
import katsu.KEntity;

import java.util.List;

/**
 * Created by shaun on 22/08/2015.
 */
public class PassageWayBase extends KEntity {

    private boolean hasDissolved = false;

    public PassageWayBase() {
        super();
        this.setzLayer(5);
    }

    @Override
    public void update() {
        super.update();
        if (!hasDissolved) {
            List<KEntity> entities = getRoom().findEntitiesAtPoint(getX()+1, getY()+1);
            for (KEntity e : entities) {
                e.setSolid(false);
            }
            hasDissolved = true;
        }

    }
}
