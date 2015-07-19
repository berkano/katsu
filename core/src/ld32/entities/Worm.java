package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import katsu.KDirection;
import katsu.KEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Worm extends Mob {
    @Override
    public void onCollide(KEntity other) {
        super.onCollide(other);
        if (other instanceof Mole) {
            if (World.numLives < 5 && this.getHealth() > 0) {
                World.numLives++;
                this.setHealth(0);
//                LD32Sounds.mole_eat.play();
                LD32Sounds.eat_worm.play();
            }
        }
    }

    @Override
    public void update() {
        super.update();
        moveRequested(KDirection.random());
    }
}
