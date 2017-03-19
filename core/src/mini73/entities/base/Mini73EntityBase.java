package mini73.entities.base;

import katsu.KEntity;
import katsu.KRoom;
import mini73.UnfinishedBusinessException;

/**
 * Created by shaun on 18/03/2017.
 */
public class Mini73EntityBase extends KEntity {

    private boolean collisionTarget = true;
    public boolean orientSpriteByMovement = false;

    public void setCollisionTarget(boolean collisionTarget) {
        this.collisionTarget = collisionTarget;
    }

    public void beforeDeath(KRoom room) {
        throw new UnfinishedBusinessException();
    }
}
