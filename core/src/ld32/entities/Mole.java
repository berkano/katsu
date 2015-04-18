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
        lookAtMe();
    }

    @Override
    public void onMoved() {
        super.onMoved();
        lookAtMe();
    }

    @Override
    public void render() {
        super.render();
        lookAtMe();
    }

    private void lookAtMe() {
        Panko.getMainCamera().position.x = getX();
        Panko.getMainCamera().position.y = getY();
    }

}
