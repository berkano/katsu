package ld31v2.entities;

import panko.Panko;
import panko.PankoEntity;
import panko.PankoEntityBase;

/**
 * Created by shaun on 07/12/2014.
 */
public class Mob extends PankoEntityBase {

    public Mob() {
        super();
        this.setSolid(true);
    }

    @Override
    public void update() {
        super.update();
        if (getTarget() != null) {
            // Check it hasn't been destroyed
            if (getTarget().getRoom().getEntities().contains(getTarget())) {
                stepTowards(getTarget());
            } else {
                setTarget(null);
            }
        }
        Panko.queueEntityToTop(this);
    }

    private void stepTowards(PankoEntity target) {
        int targetX = target.getX();
        int targetY = target.getY();
        int x = getX();
        int y = getY();

        int nx = x;
        int ny = y;

        if (x < targetX) nx = nx + 1;
        if (y < targetY) ny = ny + 1;
        if (x > targetX) nx = nx - 1;
        if (y > targetY) ny = ny - 1;

        tryMove(nx, ny);

    }

    private void tryMove(int nx, int ny) {

        boolean collisionDetected = false;

        // First check for collisions and fire events as necessary
        for (PankoEntity e : getRoom().getEntities()) {

            if (!e.isSolid()) continue;
            if (!e.wouldOverlap(this, nx, ny)) continue;
            if (e.equals(this)) continue;

            collisionDetected = true;
            e.onCollide(this);
            onCollide(e);
        }

        // If no collisions then do the move
        if (!collisionDetected) {
            setX(nx);
            setY(ny);
        }

    }
}
