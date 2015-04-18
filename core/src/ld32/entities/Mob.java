package ld32.entities;

import panko.PankoDirection;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mob extends SolidEntity {

    public Mob() {
        super();
        setMaxMoveInterval(1000);
    }

    public void moveRequested(PankoDirection direction) {

        if (moveGrid(direction.dx(), direction.dy())) {
            setSpriteRotation(direction.rotation());
        }
    }

}
