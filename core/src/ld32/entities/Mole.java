package ld32.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mole extends PankoEntityBase {
    @Override
    public void update() {
        super.update();
        Panko.getMainCamera().position.x = getX();
        Panko.getMainCamera().position.y = getY();
    }
}
