package pankosample.entities;

import com.badlogic.gdx.Input;
import panko.Panko;
import panko.PankoEntityBase;
import panko.PankoLog;

/**
 * Created by shaun on 16/11/2014.
 */
public class Robot extends PankoEntityBase {

    public Robot() {
        this.setSolid(true);
        Panko.getInputMultiplexer().addProcessor(this);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch(keycode) {
            case Input.Keys.W:
                    moveGrid(0, 1);
                    return true;
            case Input.Keys.A:
                moveGrid(-1, 0);
                return true;
            case Input.Keys.S:
                moveGrid(0, -1);
                return true;
            case Input.Keys.D:
                moveGrid(1, 0);
                return true;
            default:
                return super.keyDown(keycode);
        }

    }

}
