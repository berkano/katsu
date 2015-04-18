package ld32.entities;

import panko.PankoDirection;

/**
 * Created by shaun on 18/04/2015.
 */
public class Spider extends Mob {
    @Override
    public void update() {
        super.update();
        moveRequested(PankoDirection.random());
    }
}
