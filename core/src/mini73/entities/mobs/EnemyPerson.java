package mini73.entities.mobs;

import katsu.KEntity;
import mini73.Sounds;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class EnemyPerson extends FriendlyMob {

    @Override
    public boolean collide(KEntity other) {
        if (other instanceof PlayerPerson) {
            other.health -= 10;
            Sounds.hurt.play();
        }
        return true;
    }
}
