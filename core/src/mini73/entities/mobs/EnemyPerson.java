package mini73.entities.mobs;

import katsu.KEntity;
import katsu.KTiledMapEntity;
import mini73.Sounds;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@KTiledMapEntity
public class EnemyPerson extends FriendlyMob {

    public boolean collide(KEntity other) {
        if (other instanceof PlayerPerson) {
            other.addHealth(-10);
            Sounds.hurt.play();
        }
        return true;
    }
}
