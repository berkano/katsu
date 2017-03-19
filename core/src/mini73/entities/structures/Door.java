package mini73.entities.structures;

import com.badlogic.gdx.Application;
import katsu.KEntity;
import mini73.entities.base.FixedItem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class Door extends FixedItem {

    boolean firstUpdate = true;

    public Door() {
        this.setSolid(false);
        this.setCollisionTarget(true);
    }

    @Override
    public void update() {
        super.update();

        if (firstUpdate) {
            // Make anything underneath non solid
            ArrayList<KEntity> entities = getRoom().findEntitiesAtPoint(getX() + 2, getY() + 2);
            for (KEntity e : entities) {
                if (e instanceof FixedItem) {
                    e.setSolid(false);
                }
            }
            firstUpdate = false;
        }
    }

}
