package ld28.entities.mobs;

import katsu.Entity;
import ld28.PlaceNames;
import ld28.Sounds;
import ld28.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class EnemyShip extends FriendlyMob {

    public EnemyShip() {
        super();
        friendlyName = PlaceNames.randomShipName();
        this.orientSpriteByMovement = true;

    }

    @Override
    public boolean collide(Entity other) {
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
