package ld31v2.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import ld31v2.CampaignMap;
import panko.Panko;
import panko.PankoEntity;
import panko.PankoEntityBase;

import java.util.ArrayList;

/**
 * Created by shaun on 07/12/2014.
 */
public class Mob extends PankoEntityBase {

    private long lastPathFind = 0;
    private Integer intermediateTargX = null;
    private Integer intermediateTargY = null;

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
                stepTowardsPathFinding(getTarget());
            } else {
                setTarget(null);
            }
        }
        Panko.queueEntityToTop(this);
    }

    private void stepTowardsPathFinding(PankoEntity target) {

        doPathFinding(target);

        // If we have an intermediate target then step towards it linearly
        if (intermediateTargX != null) {
            if (intermediateTargY != null) {
                linearStepTowardsPoint(getX(), getY(), intermediateTargX, intermediateTargY);
            }
        }

    }

    private void doPathFinding(PankoEntity target) {

        // Don't path find too often.
        if (System.currentTimeMillis() - lastPathFind < 250) return;

        lastPathFind = System.currentTimeMillis();

        GridMap pathMap = CampaignMap.getPathMap();
        GridLocation start = new GridLocation(Math.round(getX() / getWidth()), Math.round(getY() / getHeight()), false);
        GridLocation end = new GridLocation(target.getX() / target.getWidth(), target.getY() / target.getHeight(), false);
        GridPathfinding gridPathfinding = new GridPathfinding();

        // Don't block start of path where entity is
        double prevStartVal = pathMap.get(start.getX(), start.getY());
        double prevEndVal = pathMap.get(end.getX(), end.getY());
        pathMap.set(start.getX(), start.getY(), 1);
        pathMap.set(end.getX(), end.getY(), 1);
        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);
        pathMap.set(start.getX(), start.getY(), prevStartVal);
        pathMap.set(end.getX(), end.getY(), prevEndVal);

        if (gridPath != null) {
            ArrayList<GridLocation> gridLocations = gridPath.getList();
            GridLocation gridLocation = null;
            if (gridLocations.size() >= 2) {
                gridLocation = gridLocations.get(gridLocations.size() - 2);
            }
            if (gridLocation != null) {
                // Don't change intermediate target if we are not nicely aligned on a cell
                boolean misAligned = false;
                if (intermediateTargX != null) {
                    if (intermediateTargY != null) {
                        if (getX() % getWidth() != 0) misAligned = true;
                        if (getY() % getHeight() != 0) misAligned = true;
                    }
                }
                if (!misAligned) {
                    intermediateTargX = gridLocation.getX() * getWidth();
                    intermediateTargY = gridLocation.getY() * getHeight();
                }
            }
        }

    }

    private void stepTowardsNaive(PankoEntity target) {
        int targetX = target.getX();
        int targetY = target.getY();
        int x = getX();
        int y = getY();

        linearStepTowardsPoint(x, y, targetX, targetY);

    }

    private void linearStepTowardsPoint(int x, int y, int targetX, int targetY) {
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
