package mini73.entities.base;

import katsu.KEntity;

/**
 * Created by shaun on 15/12/13.
 */
public abstract class Resource extends KEntity {
    @Override
    public boolean collide(KEntity other) {
        return false;
    }
}
