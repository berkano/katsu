package ld32.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import ld32.LD32Settings;
import ld32.LD32Sounds;
import ld32.World;
import katsu.K;
import katsu.KDirection;
import katsu.KEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class Mole extends Mob {

    private long lastDig = K.currentTime();
    private int maxDigInterval = 500;
    public boolean invincible = true;
    private long invincibleUntil = K.currentTime();
    private long lastEnemyPathFind = K.currentTime();

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
        if (getInvincibleUntil() < K.currentTime()) {
            invincible = false;
        }
        doEnemyPathFinding();
    }

    private void doEnemyPathFinding() {
        if (lastEnemyPathFind > K.currentTime() - LD32Settings.get().enemyPathFindInterval) return;
        lastEnemyPathFind = K.currentTime();

        int pfDistance = LD32Settings.get().enemyPathFindingDistance;

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();

        // For each enemy within a certain distance, calculate a path to the mole and set it as their preferred next movement
        for (KEntity e : getRoom().getEntities()) {
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
                            if (nextMove.getX() < e.getGridX())  ((Spider) e).setPathFinderNextDirection(KDirection.LEFT);
                            if (nextMove.getX() > e.getGridX())  ((Spider) e).setPathFinderNextDirection(KDirection.RIGHT);
                            if (nextMove.getY() < e.getGridY())  ((Spider) e).setPathFinderNextDirection(KDirection.DOWN);
                            if (nextMove.getY() > e.getGridY())  ((Spider) e).setPathFinderNextDirection(KDirection.UP);
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

        for (KEntity e : getRoom().getEntities()) {
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

        if (LD32Settings.get().displayPathFindingHints) {

            // Remove any existing
            for (KEntity e : getRoom().getEntities()) {
                if (e instanceof PathFindingHint) {
                    e.setHealth(0);
                    destroy();
                }
            }

            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    if (pathMap.get(x,y) == -1) {
                        PathFindingHint hint = new PathFindingHint();
                        hint.setX(x * getWidth());
                        hint.setY(y * getHeight());
                        addNewEntity(hint);
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
            if ((K.currentTime() % 500) < 250) shouldRender = false;
        }

         if (shouldRender) super.render();
        lookAtMe();
    }

    private void lookAtMe() {
        K.getMainCamera().position.x = getX();
        K.getMainCamera().position.y = getY();
    }

    public void digRequested() {

        if (World.poop >= LD32Settings.get().maxPoop) {
            K.explain("Can't dig no more, need to poop! (Press P then move away quick...)");
            return;
        }

        if (lastDig < K.currentTime() - maxDigInterval) {

            if (getFacing() != null) {

                // Target point to check is the middle of me... then 1 grid size in the direction being faced
                int myMiddleX = getX() + getWidth() / 2;
                int myMiddleY = getY() + getHeight() / 2;
                int checkX = myMiddleX + getWidth() * getFacing().dx();
                int checkY = myMiddleY + getHeight() * getFacing().dy();

                ArrayList<KEntity> digEntities = getRoom().findEntitiesAtPoint(checkX, checkY);

                for (KEntity e : digEntities) {
                    if (e instanceof Dirt) {
                        e.createInPlace(EmptyDirt.class);
                        e.setHealth(0);
                        LD32Sounds.mole_dig.play();
                        World.poop++;
                        if (World.poop > LD32Settings.get().maxPoop) World.poop = LD32Settings.get().maxPoop;
                    }
                }

                lastDig = K.currentTime();
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

        if (World.poop < LD32Settings.get().maxPoop) return;

        World.poop = 0;
        Poop poop = new Poop();
        poop.setX(getX());
        poop.setY(getY());
        poop.setRoom(getRoom());
        addNewEntity(poop);
        LD32Sounds.poop.play();

    }

    public void tryLoseLife() {

        if (invincible) return;

        World.numLives -= 1;
        invincible = true;
        setInvincibleUntil(K.currentTime() + 5000);
        LD32Sounds.hurt.play();
        K.explain("Life lost! :-(");

    }
}
