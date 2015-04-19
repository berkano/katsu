package ld32.entities;

import panko.Panko;
import panko.PankoEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 19/04/2015.
 */
public class MobKillingBlock extends RotatingTerrain {

    private long lastFallTryMillis = Panko.currentTime();

    @Override
    public void render() {
        if (Panko.random.nextInt(10) == 0) {
            setSpriteRotation(Panko.random.nextInt(10) - 5);
        }
        super.render();
    }

    @Override
    public void update() {
        super.update();

        fallIfPossible();
        Panko.queueEntityToTop(this);


    }

    private void fallIfPossible() {

        if (lastFallTryMillis > Panko.currentTime() - 500) return;
        lastFallTryMillis = Panko.currentTime();


        // Directly below
        if (tryFallDown()) return;

        // Randomly diag left down or diag right down - unless it's rock
        if (this instanceof Rock) return;

        boolean rightFirst = Panko.random.nextBoolean();

        if (rightFirst) {
            if (tryFallDownRight()) return;
            tryFallDownLeft();

        } else {
            if (tryFallDownLeft()) return;
            tryFallDownRight();
        }

    }

    private boolean tryFallDownLeft() {
        return tryFall(-1, -1, getX() - getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFallDownRight() {
        return tryFall(1, -1, getX() + 3 * getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFallDown() {
        return tryFall(0, -1, getX() + getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFall(int dxGrid, int dyGrid, int checkX, int checkY) {

        // check if we can fall directly down
        boolean canFall = true;
        ArrayList<PankoEntity> entities = getRoom().findEntitiesAtPoint(checkX, checkY);
        for (PankoEntity e : entities) {
            if (!(e instanceof EmptyDirt) && !(e instanceof Mob) && !(e instanceof Web)) {
                canFall = false;
                if (this instanceof Lava) {
                    Panko.breakpoint();
                }
            }
        }

        if (canFall) {
            moveGrid(dxGrid, dyGrid);
        }

        return canFall;

    }
}
