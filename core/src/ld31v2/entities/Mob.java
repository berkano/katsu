package ld31v2.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import ld31v2.CampaignMap;
import ld31v2.WarGame;
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
    private long lastCheckedNeighboursForCombat = 0;
    private long lastTimeAlignedOK = 0;
    private long lastAITime = 0;

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
        checkNeighboursForCombat();
        Panko.queueEntityToTop(this);
        checkStuckAndDieIfNecessary();
//        if (!(this instanceof ControllableMob)) {
        runAI();
//        }
    }

    private void runAI() {

        // Don't run AI all the time
        if (lastAITime > System.currentTimeMillis() - 100) return;
        lastAITime = System.currentTimeMillis();

        /*
         Strategies:

         - Move from spawner pad to castle wall
         - Go to the nearest:
             castle which is not this one, as long as it would maintain a majority in this castle
             or enemy player (whichever is closest

          */

        if (isOnSpawner()) {
            targetNearestBaseComponent();
            return;
        }

        if (isInBase()) {
            if (weWouldHaveTheMajorityIfILeft()) {
                targetNearestBaseOrEnemy();
            }
        } else {
            targetNearestBaseOrEnemy();
        }

    }

    private void targetNearestBaseOrEnemy() {

        PankoEntity closest = null;
        int closestDist = 9999;

        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof BaseComponent || e instanceof Mob) {

                // Ignore soldiers from same player
                if (e.getClass().equals(getClass())) continue;

                int dx = e.getGridX() - getGridX();
                int dy = e.getGridY() - getGridY();
                int d = (int)Math.round(Math.sqrt(dx*dx+dy*dy));

                // Don't target bases we already have a majority for
                if (e instanceof BaseComponent) {
                    Spawner spawner = nearestSpawnerTo(e);
                    if (spawner != null) {
                        if (spawnerHasMajorityFor(spawner, this)) continue;
                    }
                }

                if (d < closestDist) {
                    closestDist = d;
                    closest = e;
                }

            }
        }

        if (closest != null) {
            setTarget(closest);
        }
    }

    private boolean spawnerHasMajorityFor(Spawner spawner, Mob mob) {

        int player = 0;
        if (mob instanceof SoldierP1) player = 1;
        if (mob instanceof SoldierP2) player = 2;
        if (mob instanceof SoldierP3) player = 3;

        if (getMajorityPlayer(spawner, 0) == player) return true;
        return false;

    }

    private Spawner nearestSpawnerTo(PankoEntity entity) {

        PankoEntity closest = null;
        int closestDist = 9999;

        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof Spawner) {

                int dx = e.getGridX() - entity.getGridX();
                int dy = e.getGridY() - entity.getGridY();
                int d = (int)Math.round(Math.sqrt(dx*dx+dy*dy));

                if (d < closestDist) {
                    closestDist = d;
                    closest = e;
                }

            }
        }

        return (Spawner)closest;


    }

    private boolean weWouldHaveTheMajorityIfILeft() {

        // First work out where the spawner is

        Spawner spawner = null;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (PankoEntity e : getRoom().getEntities()) {
                    if (e.getGridX() == getGridX() + x) {
                        if (e.getGridY() == getGridY() + y) {
                            if (e instanceof Spawner) {
                                spawner = (Spawner) e;
                            }
                        }
                    }
                }
            }
        }

        if (spawner == null) return false;

        int majorityPlayer = getMajorityPlayer(spawner, 2);

        if (this instanceof SoldierP1 && majorityPlayer == 1) return true;
        if (this instanceof SoldierP2 && majorityPlayer == 2) return true;
        if (this instanceof SoldierP3 && majorityPlayer == 3) return true;

        return false;
    }

    private int getMajorityPlayer(Spawner spawner, int neededMajority) {
        // work out which player has the majority
        int p1count = 0;
        int p2count = 0;
        int p3count = 0;
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (PankoEntity e : getRoom().getEntities()) {
                    if (e.getGridX() == spawner.getGridX() + x) {
                        if (e.getGridY() == spawner.getGridY() + y) {
                            if (e instanceof SoldierP1) p1count++;
                            if (e instanceof SoldierP2) p2count++;
                            if (e instanceof SoldierP3) p3count++;
                        }
                    }
                }
            }
        }

        int majority = 0;
        if (p1count > (p2count+neededMajority) && p1count > (p3count+neededMajority)) majority = 1;
        if (p2count > (p1count+neededMajority) && p2count > (p3count+neededMajority)) majority = 2;
        if (p3count > (p2count+neededMajority) && p3count > (p1count+neededMajority)) majority = 3;

        return majority;

    }

    private boolean isInBase() {
        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof Spawner || e instanceof BaseComponent) {
                if (e.overlaps(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void targetNearestBaseComponent() {
        // Find part of base which is not occupied and try to move there


        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                boolean canMoveToSpace = true;
                PankoEntity potentialTarget = null;
                for (PankoEntity e : getRoom().getEntities()) {
                    if (e.getGridX() == getGridX() + x) {
                        if (e.getGridY() == getGridY() + y) {
                            if (e instanceof Mob) {
                                canMoveToSpace = false;
                            } else {
                                potentialTarget = e;
                            }
                        }
                    }
                }
                if (canMoveToSpace) {
                    if (potentialTarget != null) {
                        setTarget(potentialTarget);
                    }
                }

            }

        }

    }

    private boolean isOnSpawner() {
        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof Spawner) {
                if (e.overlaps(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkStuckAndDieIfNecessary() {

        boolean misAligned = false;
        if (getX() % getWidth() != 0) misAligned = true;
        if (getY() % getHeight() != 0) misAligned = true;

        if (!misAligned) {
            lastTimeAlignedOK = System.currentTimeMillis();
        } else {
            if (lastTimeAlignedOK < System.currentTimeMillis() - 5000) {
                Panko.queueRemoveEntity(this);
                Panko.getUI().writeText("@PINK A soldier got stuck and died!");
                WarGame.death.play();
            }
        }
    }

    private void checkNeighboursForCombat() {
        if (lastCheckedNeighboursForCombat > System.currentTimeMillis() - 500) return;
        lastCheckedNeighboursForCombat = System.currentTimeMillis();
        for (PankoEntity e : getRoom().getEntities()) {
            int eGridX = e.getGridX();
            int eGridY = e.getGridY();
            int gridX = getGridX();
            int gridY = getGridY();
            int dx = Math.abs(eGridX - gridX);
            int dy = Math.abs(eGridY - gridY);
            if (dx <= 1 && dy <= 1) {
                if (e instanceof Mob) {
                    doCombat(this, e);
                }
            }
        }
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
            if (e instanceof Mob) {
                doCombat(this, e);
            }
        }

        // If no collisions then do the move
        if (!collisionDetected) {
            setX(nx);
            setY(ny);
        }

    }

    private void doCombat(PankoEntity mob1, PankoEntity mob2) {

        // Check if one already died
        if (mob1.isBeingRemoved()) return;
        if (mob2.isBeingRemoved()) return;

        // No combat between soldiers for same player
        if (mob1.getClass().equals(mob2.getClass())) return;

        boolean mob1Wins = Panko.random.nextBoolean();

        Skeleton skeleton = new Skeleton();

        if (mob1Wins) {
            skeleton.setX(mob2.getX());
            skeleton.setY(mob2.getY());
            Panko.queueRemoveEntity(mob2);
            showDeathMessage(mob2);
        } else {
            skeleton.setX(mob1.getX());
            skeleton.setY(mob1.getY());
            Panko.queueRemoveEntity(mob1);
            showDeathMessage(mob1);
        }

        Panko.queueEntityToRoom(getRoom(), skeleton);

    }

    private void showDeathMessage(PankoEntity mob) {

        WarGame.death.play();

        if (mob instanceof SoldierP1) {
            Panko.getUI().writeText("@PINK Blue loses a soldier!");
        }
        if (mob instanceof SoldierP2) {
            Panko.getUI().writeText("@PINK Red loses a soldier!");
        }
        if (mob instanceof SoldierP3) {
            Panko.getUI().writeText("@PINK Purple loses a soldier!");
        }

    }
}
