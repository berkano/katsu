package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import panko.Panko;
import panko.PankoDirection;
import panko.PankoEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Spider extends Mob {
    @Override
    public void update() {
        super.update();
        moveRequested(PankoDirection.random());
    }

    @Override
    public void onCollide(PankoEntity other) {
        super.onCollide(other);
        if (other instanceof Mole) {
            Mole mole = (Mole) other;
            if (mole.invincible == false) {
                World.numLives -= 1;
                mole.invincible = true;
                mole.setInvincibleUntil(Panko.currentTime() + 5000);
                LD32Sounds.mole_die.play();
            }
        }
    }
}
