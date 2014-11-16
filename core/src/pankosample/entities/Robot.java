package pankosample.entities;

import com.badlogic.gdx.Input;
import panko.Panko;
import panko.PankoEntityBase;
import pankosample.Sounds;

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
            movePlayerIfInput(Input.Keys.W, 0, 1);
            movePlayerIfInput(Input.Keys.A, -1, 0);
            movePlayerIfInput(Input.Keys.S, 0, -1);
            movePlayerIfInput(Input.Keys.D, 1, 0);
        }
    }

    private void movePlayerIfInput(int key, int dx, int dy) {
        if (Panko.isKeyDown(key)) {
            if (!moveGrid(dx, dy)) Sounds.cantMove.play();
        }
    }

}
