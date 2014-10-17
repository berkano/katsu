package ld28.entities.base;

import katsu.Entity;

/**
 * Created by shaun on 15/12/13.
 */
public abstract class Resource extends Entity {
    @Override
    public boolean collide(Entity other) {
        return false;
    }
}
