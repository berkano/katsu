package ld28.entities.mobs;

import katsu.Entity;
import ld28.Sounds;
import ld28.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class EnemyPerson extends FriendlyMob {

    @Override
    public boolean collide(Entity other) {
        if (other instanceof PlayerPerson) {
            other.health -= 10;
            Sounds.hurt.play();
        }
        return true;
    }
}
