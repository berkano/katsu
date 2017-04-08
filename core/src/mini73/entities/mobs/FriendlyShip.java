package mini73.entities.mobs;

import katsu.KTiledMapEntity;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@KTiledMapEntity
public class FriendlyShip extends FriendlyMob {
    public FriendlyShip() {
        super();
        this.orientSpriteByMovement = true;

    }

}
