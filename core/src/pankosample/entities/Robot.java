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
    public void update() {
        super.update();

        if (lastMovedMoreThan(50)) { // One grid move per this interval

            if (Panko.isKeyDown(Input.Keys.W)) {
                moveGrid(0, 1);
            }
            if (Panko.isKeyDown(Input.Keys.A)) {
                moveGrid(-1, 0);
            }
            if (Panko.isKeyDown(Input.Keys.S)) {
                moveGrid(0, -1);
            }
            if (Panko.isKeyDown(Input.Keys.D)) {
                moveGrid(1, 0);
            }
        }

    }

}
