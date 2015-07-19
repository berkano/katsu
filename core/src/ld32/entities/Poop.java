package ld32.entities;

import ld32.LD32Settings;
import ld32.LD32Sounds;
import katsu.K;
import katsu.KEntity;
import katsu.KEntityBase;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class Poop extends KEntityBase {

    private long start = K.currentTime();

    @Override
    public void update() {
        long ageMillis = K.currentTime() - start;

        if (ageMillis > 5000) {
            if (getHealth() > 0) {
                setHealth(0);
                destroy();
                LD32Sounds.poop_explode.play();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {

                        // Nerf for now
                        if (LD32Settings.get().nerfLava) {
                            if (dx != 0 || dy != 0) continue;
                        }

                        // Only create lava if there is empty dirt underneath
                        ArrayList<KEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth() / 2, getY() + getHeight()/2);
                        boolean canCreate = true;
                        for (KEntity e : entities) {
                            if (!(e instanceof EmptyDirt) && !(e instanceof Mob) && !(e instanceof Poop)) {
                                canCreate = false;
                            }
                        }

                        if (canCreate) {
                            Lava lava = new Lava();
                            lava.setX(getX() + dx * getHeight());
                            lava.setY(getY() + dy * getHeight());
                            lava.setRoom(getRoom());
                            addNewEntity(lava);
                        }
                    }
                }
            }
        }
    }
}
