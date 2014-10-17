package ld28.entities.terrain;

import com.badlogic.gdx.Application;
import katsu.Entity;
import katsu.entities.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
public class Water extends FixedItem {

    public boolean hasPickedTile = false;

    @Override
    public boolean collide(Entity other) {
        return true;
    }

    public Water() {
        this.solid = false;
        this.isCollisionTarget = false;
    }


    @Override
    public void update(Application gc) {
        super.update(gc);    //To change body of overridden methods use File | Settings | File Templates.

    }


}
