package ld32.entities;

import ld32.LD32Sounds;
import ld32.World;
import panko.PankoDirection;
import panko.PankoEntity;

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
