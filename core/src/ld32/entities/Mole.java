package ld32.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import ld32.LD32Settings;
import ld32.LD32Sounds;
import ld32.World;
import panko.Panko;
import panko.PankoDirection;
import panko.PankoEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mole extends Mob {

    private long lastDig = Panko.currentTime();
    private int maxDigInterval = 500;
    public boolean invincible = true;
    private long invincibleUntil = Panko.currentTime();
    private long lastEnemyPathFind = Panko.currentTime();

    public Mole() {
        super();
        setMaxMoveInterval(100);
        lookAtMe();
    }

    @Override
    public void onMoved() {
        super.onMoved();
        LD32Sounds.mole_move.play();
        lookAtMe();
    }

    @Override
    public void update() {
        super.update();
        if (getInvincibleUntil() < Panko.currentTime()) {
            invincible = false;
        }
        doEnemyPathFinding();
    }

    private void doEnemyPathFinding() {
        if (lastEnemyPathFind > Panko.currentTime() - LD32Settings.enemyPathFindInterval) return;
        lastEnemyPathFind = Panko.currentTime();

        int pfDistance = LD32Settings.enemyPathFindingDistance;

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();

        // For each enemy within a certain distance, calculate a path to the mole and set it as their preferred next movement
        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof Spider) {
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
//                            Panko.getUI().writeText("Spider " + e.toString() + " next move would be " + nextMove.getX() + "," + nextMove.getY());
                            if (nextMove.getX() < e.getGridX())  ((Spider) e).setPathFinderNextDirection(PankoDirection.LEFT);
                            if (nextMove.getX() > e.getGridX())  ((Spider) e).setPathFinderNextDirection(PankoDirection.RIGHT);
                            if (nextMove.getY() < e.getGridY())  ((Spider) e).setPathFinderNextDirection(PankoDirection.DOWN);
                            if (nextMove.getY() > e.getGridY())  ((Spider) e).setPathFinderNextDirection(PankoDirection.UP);
                        }
                    }
                }
            }
        }


//        GridPathfinding gridPathfinding = new GridPathfinding();
//
//        // Don't block start of path where entity is
//        double prevStartVal = pathMap.get(start.getX(), start.getY());
//        double prevEndVal = pathMap.get(end.getX(), end.getY());
//        pathMap.set(start.getX(), start.getY(), 1);
//        pathMap.set(end.getX(), end.getY(), 1);
//        GridPath gridPath = gridPathfinding.getPath(start, end, pathMap);
//        pathMap.set(start.getX(), start.getY(), prevStartVal);
//        pathMap.set(end.getX(), end.getY(), prevEndVal);




    }

    private GridMap createPathMap() {

        GridMap pathMap = new GridMap(100, 100);

        for (PankoEntity e : getRoom().getEntities()) {
            if (e instanceof Spider) continue; // Don't let spiders block their own paths
            if (e instanceof EmptyDirt) continue;
            if (e instanceof Web) continue;
            if (e instanceof WayPoint) continue;
            if (e instanceof Mole) continue; // otherwise can't get there
            if (e.isSolid() || (e instanceof MobKillingBlock)) {

                int cellX = e.getGridX();
                int cellY = e.getGridY();

                if (cellX >= 0 && cellY >= 0 && cellX <= 100 && cellY <= 100) {
                    pathMap.set(cellX, cellY, GridMap.WALL);
                }
            }
        }

        if (LD32Settings.displayPathFindingHints) {

            // Remove any existing
            for (PankoEntity e : getRoom().getEntities()) {
                if (e instanceof PathFindingHint) {
                    e.setHealth(0);
                    getRoom().getDeadEntities().add(e);
                }
            }

            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    if (pathMap.get(x,y) == -1) {
                        PathFindingHint hint = new PathFindingHint();
                        hint.setX(x * getWidth());
                        hint.setY(y * getHeight());
                        getRoom().getNewEntities().add(hint);
                        hint.setRoom(getRoom());
                    }
                }
            }
        }

        return pathMap;

    }

    @Override
    public void render() {

        boolean shouldRender = true;
        // When not harmable flicker the rendering
        if (invincible) {
            if ((Panko.currentTime() % 500) < 250) shouldRender = false;
        }

         if (shouldRender) super.render();
        lookAtMe();
    }

    private void lookAtMe() {
        Panko.getMainCamera().position.x = getX();
        Panko.getMainCamera().position.y = getY();
    }

    public void digRequested() {

        if (World.poop >= LD32Settings.maxPoop) {
            Panko.explain("Can't dig no more, need to poop! (Press P then move away quick...)");
            return;
        }

        if (lastDig < Panko.currentTime() - maxDigInterval) {

            if (getFacing() != null) {

                // Target point to check is the middle of me... then 1 grid size in the direction being faced
                int myMiddleX = getX() + getWidth() / 2;
                int myMiddleY = getY() + getHeight() / 2;
                int checkX = myMiddleX + getWidth() * getFacing().dx();
                int checkY = myMiddleY + getHeight() * getFacing().dy();

                ArrayList<PankoEntity> digEntities = getRoom().findEntitiesAtPoint(checkX, checkY);

                for (PankoEntity e : digEntities) {
                    if (e instanceof Dirt) {
                        e.createInPlace(EmptyDirt.class);
                        e.setHealth(0);
                        Panko.queueEntityToTop(this);
                        LD32Sounds.mole_dig.play();
                        World.poop++;
                        if (World.poop > LD32Settings.maxPoop) World.poop = LD32Settings.maxPoop;
                    }
                }

                lastDig = Panko.currentTime();
            }
        }

    }

    public long getInvincibleUntil() {
        return invincibleUntil;
    }

    public void setInvincibleUntil(long invincibleUntil) {
        this.invincibleUntil = invincibleUntil;
    }

    public void poopRequested() {

        if (World.poop < LD32Settings.maxPoop) return;

        World.poop = 0;
        Poop poop = new Poop();
        poop.setX(getX());
        poop.setY(getY());
        poop.setRoom(getRoom());
        getRoom().getNewEntities().add(poop);
        LD32Sounds.mole_poop.play();

    }

    public void tryLoseLife() {

        if (invincible) return;

        World.numLives -= 1;
        invincible = true;
        setInvincibleUntil(Panko.currentTime() + 5000);
        LD32Sounds.mole_die.play();

    }
}
