package mini73.entities.terrain;

import com.badlogic.gdx.Application;
import katsu.KEntity;
import katsu.TiledMapEntity;
import mini73.entities.base.FixedItem;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 28/04/13
 * Time: 09:50
 * To change this template use File | Settings | File Templates.
 */
@TiledMapEntity
public class Asteroid extends FixedItem {

    public boolean hasPickedTile = false;

    public Asteroid() {
        this.setSolid(true);
        this.setCollisionTarget(true);
    }

}
