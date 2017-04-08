package mini73.entities.terrain;

import katsu.KTiledMapEntity;
import mini73.entities.base.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
@KTiledMapEntity
public class Water extends FixedItem {

    public Water() {
        this.setSolid(false);
        this.setCollisionTarget(false);
    }

}
