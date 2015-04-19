package ld32.entities;

import ld32.LD32Sounds;
import panko.Panko;
import panko.PankoEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class Home extends LD32ScenicBase {

    private boolean wonGame = false;

    @Override
    public void update() {
        super.update();
        if (wonGame) return;

        ArrayList<PankoEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth()/2, getY() + getWidth() / 2);

        for (PankoEntity e : entities) {
            if (e instanceof Mole) {
                wonGame = true;
                Panko.getUI().writeText("You win! Reunited with le molies! Well done!");
                LD32Sounds.win_game.play();
                Panko.pauseGame();
            }
        }

    }
}
