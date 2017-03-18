package mini73.entities.mobs;

import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
public class PlayerPerson extends FriendlyMob {

    public PlayerPerson() {
        this.orientSpriteByMovement = false;
    }

    @Override
    public String toString() {
        return "Major Tim";
    }
}
