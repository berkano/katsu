package mini73.entities.structures;

import katsu.KEntity;
import mini73.entities.base.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
public class Wall extends FixedItem {

    public int getResourceCost(Class c) {
        return 0;
    }

    public Wall() {
        this.setSolid(true);
        this.setCollisionTarget(true);
    }

}
