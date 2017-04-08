package mini73.entities.structures;

import katsu.KTiledMapEntity;
import mini73.entities.base.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
@KTiledMapEntity
public class MetalWall extends FixedItem {

    public MetalWall() {
        this.setSolid(true);
        this.setCollisionTarget(true);
    }

}
