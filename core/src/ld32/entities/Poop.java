package ld32.entities;

import ld32.LD32Sounds;
import panko.Panko;
import panko.PankoEntity;
import panko.PankoEntityBase;

import java.util.ArrayList;

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
                getRoom().getDeadEntities().add(this);
                LD32Sounds.poop_explode.play();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {

                        // Nerf for now
                        if (dx != 0 || dy != 0) continue;

                        // Only create lava if there is empty dirt underneath
                        ArrayList<PankoEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth() / 2, getY() + getHeight()/2);
                        boolean canCreate = true;
                        for (PankoEntity e : entities) {
                            if (!(e instanceof EmptyDirt) && !(e instanceof Mob) && !(e instanceof Poop)) {
                                canCreate = false;
                            }
                        }

                        if (canCreate) {
                            Lava lava = new Lava();
                            lava.setX(getX() + dx * getHeight());
                            lava.setY(getY() + dy * getHeight());
                            lava.setRoom(getRoom());
                            Panko.queueEntityToTop(lava);
                            getRoom().getNewEntities().add(lava);
                        }
                    }
                }
            }
        }
    }
}
