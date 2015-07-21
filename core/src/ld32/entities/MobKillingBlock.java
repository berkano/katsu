package ld32.entities;

import katsu.K;
import katsu.KEntity;
import net.sf.jsi.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaun on 19/04/2015.
 */
public class MobKillingBlock extends RotatingTerrain {

    public MobKillingBlock() {
        super();
        setzLayer(2);
    }

    private long lastFallTryMillis = K.currentTime();

    @Override
    public void render() {
        if (K.random.nextInt(10) == 0) {
            setSpriteRotation(K.random.nextInt(10) - 5);
        }
        super.render();
    }

    @Override
    public void update() {
        super.update();
        fallIfPossible();
    }

    private void fallIfPossible() {

        if (lastFallTryMillis > K.currentTime() - 500) return;
        lastFallTryMillis = K.currentTime();

        // Grab nearby entities - overlapping
        int left = getX() - getWidth() * 3;
        int right = getX() + getWidth() * 3;
        int top = getY() + getHeight() * 3;
        int bottom = getY() - getHeight() * 3;

        List<KEntity> neighbours = getRoom().spatialSearchByIntersection(new Rectangle(left, top, right, bottom));

        if (this instanceof Lava) {
            K.breakpoint();
        }

        // Directly below
        if (tryFallDown(neighbours)) return;

        // Randomly diag left down or diag right down - unless it's rock
        if (this instanceof Rock) return;

        boolean rightFirst = K.random.nextBoolean();

        if (rightFirst) {
            if (tryFallDownRight(neighbours)) return;
            tryFallDownLeft(neighbours);

        } else {
            if (tryFallDownLeft(neighbours)) return;
            tryFallDownRight(neighbours);
        }

    }

    private boolean tryFallDownLeft(List<KEntity> neighbours) {
        return tryFall(neighbours, -1, -1, getX() - getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFallDownRight(List<KEntity> neighbours) {
        return tryFall(neighbours, 1, -1, getX() + 3 * getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFallDown(List<KEntity> neighbours) {
        return tryFall(neighbours, 0, -1, getX() + getWidth()/2, getY() - getHeight()/2);
    }

    private boolean tryFall(List<KEntity> neighbours, int dxGrid, int dyGrid, int checkX, int checkY) {

        // check if we can fall directly down
        boolean canFall = true;
        ArrayList<KEntity> entities = getRoom().findEntitiesAtPoint(neighbours, checkX, checkY);
        for (KEntity e : entities) {
            if (!(e instanceof EmptyDirt) && !(e instanceof Mob) && !(e instanceof Web)) {
                canFall = false;
                if (this instanceof Lava) {
                    K.breakpoint();
                }
            }
        }

        if (canFall) {
            moveGrid(dxGrid, dyGrid);
        }

        return canFall;

    }
}
