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

    @Override
    public int getResourceCost(Class c) {
        return 0;
    }

    public Door() {
        this.solid=false;
        this.isCollisionTarget=true;

    }

    @Override
    public void update(Application gc) {
        super.update(gc);

        if (firstUpdate) {
            // Make anything underneath non solid
            ArrayList<KEntity> entities = room.findEntitiesAtPoint(x + 2, y + 2);
            for (KEntity e : entities) {
                if (e instanceof FixedItem) {
                    e.solid = false;
                }
            }
            firstUpdate = false;
        }
    }

    @Override
    public boolean collide(KEntity other) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
