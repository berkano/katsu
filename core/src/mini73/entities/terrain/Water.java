package mini73.entities.terrain;

import com.badlogic.gdx.Application;
import katsu.KEntity;
import mini73.entities.base.FixedItem;

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
    public boolean collide(KEntity other) {
        return true;
    }

    public Water() {
        this.setSolid(false);
        this.setCollisionTarget(false);
    }


    @Override
    public void update(Application gc) {
        super.update(gc);    //To change body of overridden methods use File | Settings | File Templates.

    }


}
