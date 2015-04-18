package ld32.entities;

import panko.PankoDirection;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mob extends SolidEntity {

    public void moveRequested(PankoDirection direction) {

        setSpriteRotation(direction.rotation());
        moveGrid(direction.dx(), direction.dy());

    }

}
