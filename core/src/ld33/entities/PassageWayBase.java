package ld33.entities;

import katsu.KEntity;
import katsu.KEntityBase;

import java.util.List;

/**
 * Created by shaun on 22/08/2015.
 */
public class PassageWayBase extends KEntityBase {

    private boolean hasDissolved = false;

    public PassageWayBase() {
        super();
        this.setzLayer(5);
    }

    @Override
    public void update() {
        super.update();
        if (!hasDissolved) {
            List<KEntity> entities = getRoom().findEntitiesAtPoint(getX(), getY());
            for (KEntity e : entities) {
                e.setSolid(false);
            }
            hasDissolved = true;
        }

    }
}
