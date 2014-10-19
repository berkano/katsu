package katsu.entities;

import com.badlogic.gdx.Application;
import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import katsu.*;
import katsu.Objective;

import java.util.ArrayList;

/**
 * @author shaun
 */
public class Mob extends Entity {

    public long lastAttacked = 0;
    public long lastDamaged = 0;
    public String friendlyName = null;
    public long lastPathFind = 0;
    public Integer intermediateTargX = null;
    public Integer intermediateTargY = null;
    public long lastIntermedArrival = 0;

    public Mob() {
        this.solid = true;
        this.isCollisionTarget = true;
    }

    @Override
    // TODO bug with entity appearing twice in destroy list??
    public void beforeDeath(Room room) {
        super.beforeDeath(room);
        ui.writeText(this.toString() + " died.");
    }

    @Override
    public boolean collide(Entity other) {
        if (other == targetEntity) {
            if (currentObjective == Objective.MOVE) {
                // TODO-LD28
            }
        }
        return true;
    }

    @Override
    public String toString() {

        String mobName = "";

        if (friendlyName == null) {
            mobName = this.getClass().getSimpleName();
        } else {
            mobName = friendlyName;
        }

        return mobName;
    }

    @Override
    public void update(Application gc) {
        super.update(gc);    //To change body of overridden methods use File | Settings | File Templates.

        if (currentObjective != Objective.NOTHING) {
            if (targetEntity == null || targetEntity.wantsDestroy) {
                currentObjective = Objective.NOTHING;
                targetEntity = null;
            }
        }

        if (currentObjective == Objective.MOVE) {
            if (targetEntity != null) performMovementObjective();
            else {
                currentObjective = Objective.NOTHING;
            }
        }

    }

    private void performMovementObjective() {
        if (System.currentTimeMillis() - lastMoved > 10) {

            int newX = x;
            int newY = y;

            if (intermediateTargX == null) {
                intermediateTargX = targetEntity.x;
                intermediateTargY = targetEntity.y;
            }

            // Notice immediately when we are at intermediate path point and trigger re-calc
            if (x == intermediateTargX && y == intermediateTargY) {
                if ((x != targetEntity.x) || (y != targetEntity.y)) {
                    if (System.currentTimeMillis() - lastIntermedArrival > 250) {
                        lastIntermedArrival = System.currentTimeMillis();
                        lastPathFind = 0;
                    }
                }
            }

            // Performance - don't continually re-calc path
            if (System.currentTimeMillis() - lastPathFind > 1000) {
                lastPathFind = System.currentTimeMillis(); // + Game.instance.r.nextInt(50);
                // New: get path
                GridMap pathMap = Katsu.game.currentRoom.pathMap;
                GridLocation start = new GridLocation(Math.round(x / Settings.tileWidth), Math.round(y / Settings.tileHeight), false);
                GridLocation end = new GridLocation(targetEntity.x / Settings.tileWidth, targetEntity.y / Settings.tileHeight, false);
                GridPathfinding gridPathfinding = new GridPathfinding();

                // Don't block start of path where entity is
                double prevStartVal = pathMap.get(start.getX(), start.getY());
                double prevEndVal = pathMap.get(end.getX(), end.getY());
                pathMap.set(start.getX(), start.getY(), 1);
                pathMap.set(end.getX(), end.getY(), 1);
                GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);
                Game.pathFinds++;
                pathMap.set(start.getX(), start.getY(), prevStartVal);
                pathMap.set(end.getX(), end.getY(), prevEndVal);

                if (gridPath != null) {
                    ArrayList<GridLocation> gridLocations = gridPath.getList();
                    //GridLocation gridLocation = gridPath.getNextMove();
                    GridLocation gridLocation = null;
                    if (gridLocations.size() >= 2) {
                        gridLocation = gridLocations.get(gridLocations.size() - 2);
                    }
                    if (gridLocation != null) {
                        intermediateTargX = gridLocation.getX() * Settings.tileWidth;
                        intermediateTargY = gridLocation.getY() * Settings.tileHeight;
                    }
                }
            }

            if (intermediateTargX > x) newX = x + 1;
            if (intermediateTargX < x) newX = x - 1;
            if (intermediateTargY > y) newY = y + 1;
            if (intermediateTargY < y) newY = y - 1;

            setWantsMove(newX, newY);
        }
    }
}
