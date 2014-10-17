package katsu.entities;

import katsu.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class Resource extends Entity {

    public Resource() {
        this.solid = true;
        this.isCollisionTarget = true;
    }

    @Override
    public boolean collide(Entity other) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
