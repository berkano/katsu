package ld33.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;
import katsu.KGraphics;
import ld33.LD33Settings;

/**
 * Created by shaun on 22/08/2015.
 */
public class Monster extends MobBase {

    private boolean looksHuman = true;
    private long lastEnemyPathFind;

    public Monster() {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setFlipSpriteOnMove(true);
        this.setMaxMoveInterval(75);
        this.setzLayer(100);
    }

    @Override
    public void update() {
        super.update();
        if (!looksHuman) {
            doEnemyPathFinding();
        }
    }

    @Override
    public void render() {
        if (looksHuman) {
            setTextureRegion(KGraphics.getTextureCache().get(Human.class));
        } else {
            setTextureRegion(KGraphics.getTextureCache().get(Monster.class));
        }
        lookAtMe();
        super.render();
    }

    @Override
    public boolean moveRequested(KDirection direction) {
        boolean result = super.moveRequested(direction);
        lookAtMe();
        return result;
    }

    public boolean isLooksHuman() {
        return looksHuman;
    }

    public void setLooksHuman(boolean looksHuman) {
        this.looksHuman = looksHuman;
    }


    private void doEnemyPathFinding() {
        if (lastEnemyPathFind > K.currentTime() - LD33Settings.get().enemyPathFindInterval) return;
        lastEnemyPathFind = K.currentTime();

        int pfDistance = LD33Settings.get().enemyPathFindingDistance;

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();

        // For each enemy within a certain distance, calculate a path to the mole and set it as their preferred next movement
        for (KEntity e : getRoom().getEntities()) {
            boolean wantsPathFind = false;
            if (e instanceof MobBase) wantsPathFind = true;
            if (e instanceof Monster) wantsPathFind = false;
            if (wantsPathFind) {
                MobBase mob = (MobBase) e;
                int dx = getGridX() - e.getGridX();
                int dy = getGridY() - e.getGridY();
                if (Math.abs(dx) <= pfDistance && Math.abs(dy) <= pfDistance) {
                    GridLocation end = new GridLocation(e.getGridX(), e.getGridY(), true);
                    GridLocation start = new GridLocation(this.getGridX(), this.getGridY(), false);
                    GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

                    if (gridPath != null) {
                        GridLocation nextMove = gridPath.getNextMove(); // pop the current location
                        if (nextMove != null) nextMove = gridPath.getNextMove(); // next place
                        if (nextMove != null) {
                            if (nextMove.getX() < e.getGridX())
                                mob.setPathFinderNextDirection(KDirection.LEFT);
                            if (nextMove.getX() > e.getGridX())
                                mob.setPathFinderNextDirection(KDirection.RIGHT);
                            if (nextMove.getY() < e.getGridY())
                                mob.setPathFinderNextDirection(KDirection.DOWN);
                            if (nextMove.getY() > e.getGridY()) mob.setPathFinderNextDirection(KDirection.UP);
                        }
                    }
                }
            }
        }


    }

    private GridMap createPathMap() {

        GridMap pathMap = new GridMap(100, 100);

        for (KEntity e : getRoom().getEntities()) {
            if (e instanceof MobBase) continue;
            if (!(e.isSolid())) continue;

                int cellX = e.getGridX();
                int cellY = e.getGridY();

                if (cellX >= 0 && cellY >= 0 && cellX <= 100 && cellY <= 100) {
                    pathMap.set(cellX, cellY, GridMap.WALL);
                }
        }

        return pathMap;

    }


}
