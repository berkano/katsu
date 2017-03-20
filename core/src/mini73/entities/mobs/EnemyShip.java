package mini73.entities.mobs;

import katsu.KEntity;
import katsu.TiledMapEntity;
import mini73.Sounds;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@TiledMapEntity
public class EnemyShip extends FriendlyMob {

    public EnemyShip() {
        super();
        this.orientSpriteByMovement = true;

    }

    public boolean collide(KEntity other) {
        if (other instanceof Ship) {
            other.addHealth(-10);
            Sounds.hurt.play();
        }
        return true;
    }

}
