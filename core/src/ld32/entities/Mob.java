package ld32.entities;

import ld32.LD32Sounds;
import katsu.KDirection;
import katsu.KEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mob extends SolidEntity {

    private KDirection facing;

    public Mob() {
        super();
        setMaxMoveInterval(1000);
        setzLayer(3);
    }

    @Override
    public void update() {
        super.update();
        // Check if we are on a killing block
        for (KEntity e : getRoom().getEntities()) {
            if (e instanceof MobKillingBlock) {
                if (e.overlaps(this)) {
                    if (this instanceof Mole) {
                        ((Mole) this).tryLoseLife();
                    } else {
                        setHealth(0);
                        if (this instanceof Spider) {
                            LD32Sounds.kill_enemy.play();
                        }
                    }
                }
            }
        }
    }

    public boolean moveRequested(KDirection direction) {

        boolean result = false;

        if (moveGrid(direction.dx(), direction.dy())) {
            result = true;
            if (!(this instanceof Spider)) {
                setSpriteRotation(direction.rotation());
                setFacing(direction);
            }
        }

        // Set rotation no matter what
        if (this instanceof Mole) {
            setSpriteRotation(direction.rotation());
            setFacing(direction);
        }
        return result;
    }

    public void setFacing(KDirection facing) {
        this.facing = facing;
    }

    public KDirection getFacing() {
        return facing;
    }
}
