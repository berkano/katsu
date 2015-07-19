package ld32.entities;

import ld32.LD32Sounds;
import katsu.PankoDirection;
import katsu.PankoEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mob extends SolidEntity {

    private PankoDirection facing;

    public Mob() {
        super();
        setMaxMoveInterval(1000);
    }

    @Override
    public void update() {
        super.update();
        // Check if we are on a killing block
        for (PankoEntity e : getRoom().getEntities()) {
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

    public void moveRequested(PankoDirection direction) {

        if (moveGrid(direction.dx(), direction.dy())) {
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
    }

    public void setFacing(PankoDirection facing) {
        this.facing = facing;
    }

    public PankoDirection getFacing() {
        return facing;
    }
}
