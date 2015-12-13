package ld34.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;
import katsu.KLog;
import ld33.entities.MobBase;

/**
 * Created by shaun on 12/12/2015.
 */
public class Snowman extends LD34EntityBase {

    long didLastPathFind = K.currentTime();
    boolean hasDoneFirstPathFind = false;
    int targetGridX = 0;
    int targetGridY = 0;
    boolean hasTarget = false;

    public int getTargetGridX() {
        return targetGridX;
    }

    public void setTargetGridX(int targetGridX) {
        this.targetGridX = targetGridX;
    }

    public int getTargetGridY() {
        return targetGridY;
    }

    public void setTargetGridY(int targetGridY) {
        this.targetGridY = targetGridY;
    }

    public boolean isHasTarget() {
        return hasTarget;
    }

    public void setHasTarget(boolean hasTarget) {
        this.hasTarget = hasTarget;
    }

    public Snowman() {

        super();

        setRotateSpriteOnMove(false);
        setFlipSpriteOnMove(true);
        setMaxMoveInterval(250);
    }

    @Override
    public void render() {

        //KLog.trace("Snowman instance " + toString() + " rendering");

        lookAtMe();
        super.render();
    }


    @Override
    public void update() {

        doPathFinding();

        if (hasTarget) {
            if (getPathFinderNextDirection() != null) {
                didLastPathFind = K.currentTime();
                moveRequested(getPathFinderNextDirection());
                hasDoneFirstPathFind = true;
                setPathFinderNextDirection(null);

            }
        }


        super.update();
    }

    private void doPathFinding() {

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();

        GridLocation start = new GridLocation(getGridX(), getGridY(), false);
        GridLocation end = new GridLocation(targetGridX, targetGridY, true);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

        if (gridPath != null) {
            GridLocation nextMove = gridPath.getNextMove(); // pop the current location
            if (nextMove != null) nextMove = gridPath.getNextMove(); // next place
            if (nextMove != null) {
                if (nextMove.getX() < getGridX())
                    setPathFinderNextDirection(KDirection.LEFT);
                if (nextMove.getX() > getGridX())
                    setPathFinderNextDirection(KDirection.RIGHT);
                if (nextMove.getY() < getGridY())
                    setPathFinderNextDirection(KDirection.DOWN);
                if (nextMove.getY() > getGridY()) setPathFinderNextDirection(KDirection.UP);
            }
        }

    }

    private GridMap createPathMap() {

        GridMap pathMap = new GridMap(100, 100);

        for (KEntity e : getRoom().getEntities()) {
            if (e == this) continue;
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
