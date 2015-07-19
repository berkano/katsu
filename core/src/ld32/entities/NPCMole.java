package ld32.entities;

import katsu.PankoDirection;

/**
 * Created by shaun on 18/04/2015.
 */
public class NPCMole extends Mob {

    @Override
    public void update() {
        super.update();
        moveRequested(PankoDirection.random());
    }

}
