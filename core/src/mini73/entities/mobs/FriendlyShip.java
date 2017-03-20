package mini73.entities.mobs;

import katsu.TiledMapEntity;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@TiledMapEntity
public class FriendlyShip extends FriendlyMob {
    public FriendlyShip() {
        super();
        this.orientSpriteByMovement = true;

    }

}
