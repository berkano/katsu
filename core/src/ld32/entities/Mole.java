package ld32.entities;

import com.badlogic.gdx.Input;
import panko.Panko;
import panko.PankoDirection;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mole extends Mob {

    public Mole() {
        super();
        setMaxMoveInterval(100);
    }

    @Override
    public void update() {
        super.update();
        Panko.getMainCamera().position.x = getX();
        Panko.getMainCamera().position.y = getY();
    }

}
