package ld32.entities;

import panko.PankoDirection;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Worm extends Mob {
    @Override
    public void update() {
        super.update();
        moveRequested(PankoDirection.random());
    }
}
