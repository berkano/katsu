package mini73.entities.mobs;

import katsu.KEntity;
import mini73.Sounds;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class EnemyShip extends FriendlyMob {

    public EnemyShip() {
        super();
        this.orientSpriteByMovement = true;

    }

    @Override
    public boolean collide(KEntity other) {
        if (other instanceof Ship) {
            other.health -= 10;
            Sounds.hurt.play();
        }
        return true;
    }


    @Override
    public void afterMoved() {
        super.afterMoved();

    }
}
