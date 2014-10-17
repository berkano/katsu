package ld28.entities.structures;

import katsu.Entity;
import katsu.entities.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class MetalWall extends FixedItem {

    @Override
    public int getResourceCost(Class c) {
        return 0;
    }

    public MetalWall() {
        this.solid=true;
        this.isCollisionTarget=true;
    }

    @Override
    public boolean collide(Entity other) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
