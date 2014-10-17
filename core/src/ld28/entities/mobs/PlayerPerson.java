package ld28.entities.mobs;

import ld28.entities.base.FriendlyMob;

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
