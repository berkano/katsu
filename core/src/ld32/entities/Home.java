package ld32.entities;

import ld32.LD32Sounds;
import katsu.K;
import katsu.KEntity;

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

        ArrayList<KEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth()/2, getY() + getWidth() / 2);

        for (KEntity e : entities) {
            if (e instanceof Mole) {
                wonGame = true;
                K.getUI().writeText("");
                K.getUI().writeText("*** WIN: Noel found his way home! Well done! ***");
                K.getUI().writeText("");
                LD32Sounds.stopAllMusic();
                LD32Sounds.winMusic.loop();
                K.pauseGame();
            }
        }

    }
}
