package ld32.entities;

import ld32.LD32Sounds;
import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class Poop extends PankoEntityBase {

    private long start = Panko.currentTime();

    @Override
    public void update() {
        long ageMillis = Panko.currentTime() - start;

        if (ageMillis > 5000) {
            if (getHealth() > 0) {
                setHealth(0);
                LD32Sounds.poop_explode.play();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        Lava lava = new Lava();
                        lava.setX(getX() + dx * getHeight());
                        lava.setY(getY() + dy * getHeight());
                        lava.setRoom(getRoom());
                        getRoom().getNewEntities().add(lava);
                    }
                }
            }
        }
    }
}
