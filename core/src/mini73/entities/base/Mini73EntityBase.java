package mini73.entities.base;

import katsu.KEntity;

/**
 * Created by shaun on 18/03/2017.
 */
public class Mini73EntityBase extends KEntity {

    private boolean collisionTarget = true;

    public void setCollisionTarget(boolean collisionTarget) {
        this.collisionTarget = collisionTarget;
    }
}
