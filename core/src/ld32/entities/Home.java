package ld32.entities;

import ld32.LD32Sounds;
import katsu.Panko;
import katsu.PankoEntity;

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
                Panko.getUI().writeText("");
                Panko.getUI().writeText("*** WIN: Noel found his way home! Well done! ***");
                Panko.getUI().writeText("");
                LD32Sounds.stopAllMusic();
                LD32Sounds.winMusic.loop();
                Panko.pauseGame();
            }
        }

    }
}
