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
    int nextDX = 0;
    int nextDY = 0;
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
        setSolid(true);
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
            if (nextDX != 0 || nextDY != 0) {
                moveRequested(nextDX, nextDY);
                didLastPathFind = K.currentTime();
                hasDoneFirstPathFind = true;
                nextDX = 0;
                nextDY = 0;
            }
        }

        super.update();
    }

    private void doPathFinding() {

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();


        int startX = getGridX();
        int startY = getGridY();
        int endX = targetGridX;
        int endY = targetGridY;

        KLog.pathfinder(this, "get path from " + startX + "," + startY + " to "+endX + "," + endY);

        GridLocation start = new GridLocation(startX, startY, false);
        GridLocation end = new GridLocation(endX, endY, true);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);

        if (gridPath != null) {
            if (gridPath.getList().size() > 1) {
                GridLocation nextMove = gridPath.getList().get(gridPath.getList().size() - 2); // last entry?
                if (nextMove != null) {
                    nextDX = nextMove.getX() - getGridX();
                    nextDY = nextMove.getY() - getGridY();
                    KLog.pathfinder(this, "pathfinder suggests delta of " + nextDX + "," + nextDY);
                }
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

            if (cellX == targetGridX && cellY == targetGridY) continue; // allow attempted path finds up to the target

            if (cellX >= 0 && cellY >= 0 && cellX <= 100 && cellY <= 100) {
                pathMap.set(cellX, cellY, GridMap.WALL);
            }
        }

        return pathMap;

    }

}
