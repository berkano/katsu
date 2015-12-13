package ld34.entities;

import ext.pathfinding.grid.GridLocation;
import ext.pathfinding.grid.GridMap;
import ext.pathfinding.grid.GridPath;
import ext.pathfinding.grid.GridPathfinding;
import katsu.*;
import ld34.LD34Sounds;

import java.util.List;

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
    private int money = 0;

    boolean isTweening = false;
    int tweenToX = 0;
    int tweenToY = 0;

    public Action getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(Action targetAction) {
        this.targetAction = targetAction;
    }

    Action targetAction = null;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public enum Action {
        WALK,
        CHOP,
        PLANT,
        BUY_LAND
    }


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
        setMaxMoveInterval(100);

    }

    @Override
    public void render() {

        //KLog.trace("Snowman instance " + toString() + " rendering");
        super.render();
    }


    @Override
    public void update() {

        if (isTweening) {
            tweenMe();
            return;
        }

        doPathFinding();

        if (hasTarget) {

            // Do the moving first or we get in a tangle
            if (nextDX != 0 || nextDY != 0) {
                boolean almostThere = false;
                // if our action should be performed adjacent to the target, don't go all the way there.
                if (getGridX() + nextDX == targetGridX && getGridY() + nextDY == targetGridY) {
                    almostThere = true;
                }
                boolean goneFarEnough = false;
                if (almostThere) {
                    if (targetAction.equals(Action.PLANT) || targetAction.equals(Action.BUY_LAND) || targetAction.equals(Action.CHOP)) {
                        goneFarEnough = true;
                    }
                }

                if (!goneFarEnough) {
                    int oldX = getX();
                    int oldY = getY();
                    if (moveRequested(nextDX, nextDY)) {
                        isTweening = true;
                        setX(oldX);
                        setY(oldY);
                        tweenToX=(oldX + nextDX * K.getGridSize());
                        tweenToY=(oldY + nextDY * K.getGridSize());

                        LD34Sounds.walk.play();
                    }
                }
                didLastPathFind = K.currentTime();
                hasDoneFirstPathFind = true;
                nextDX = 0;
                nextDY = 0;
            }

            // If we have an action and a target then check if we're adjacent and do it
            int dx = targetGridX - getGridX();
            int dy = targetGridY - getGridY();
            boolean adjacent = false;

            if ((Math.abs(dx) <= 1) && (Math.abs(dy) <= 1)) {
                adjacent = true;
            }
            // but not if we're right on it
            if (dx == 0 && dy == 0) {
                adjacent = false;
            }

            boolean performedAction = false;

            if (adjacent) {
                if (targetAction == Action.CHOP) {
                    Tree toChop = (Tree) findFirstEntityOnGrid(Tree.class, targetGridX, targetGridY);
                    if (toChop != null) {
//                        K.getUI().writeText("Choppy chop!");
                        LD34Sounds.chop.play();


                        int earned = toChop.getMarketValue();
                        money += earned;
                        K.getUI().writeText("Yay, I earned £" + earned + "!");

                        toChop.destroy();
                        performedAction = true;
                    }
                }
                if (targetAction == Action.PLANT) {

                    if (gridIsEmpty(targetGridX, targetGridY)) {


                        if (money >= 1) {

                            Tree sapling = new Tree();
                            sapling.setGridX(targetGridX);
                            sapling.setGridY(targetGridY);
                            sapling.setStage(Tree.Stage.sapling);
                            getRoom().addNewEntity(sapling);
//                            K.getUI().writeText("Planty plant!");
                            LD34Sounds.plant.play();

                            // don't let snowman be on top of tree
                            targetGridX = getGridX();
                            targetGridY = getGridY();
                            money = money - 1;
                        } else {
                            K.getUI().writeText("I don't have enough money :-( Need £1");
                            LD34Sounds.gone_wrong.play();
                        }

                    } else {
                        K.getUI().writeText("I can't plant here :-(");
                        LD34Sounds.gone_wrong.play();
                    }

                    performedAction = true;

                }
                if (targetAction == Action.BUY_LAND) {
                    Land land = (Land) findFirstEntityOnGrid(Land.class, targetGridX, targetGridY);
                    if (land == null) {
                        K.getUI().writeText("There's no land to buy here :-(");
                        LD34Sounds.gone_wrong.play();
                    } else {
                        if (money < 100) {
                            K.getUI().writeText("I don't have enough money :-( Need £100");
                            LD34Sounds.gone_wrong.play();
                        } else {
                            Grass grass = new Grass();
                            grass.setX(land.getX());
                            grass.setY(land.getY());
                            getRoom().addNewEntity(grass);
                            land.destroy();
                            money -= 100;
                            K.getUI().writeText("New land! Woo!");
                            LD34Sounds.buy_land.play();

                        }
                    }
                    performedAction = true;
                }
            }

            if (performedAction) {
                targetAction = Action.WALK;
//                hasTarget = false;
//                targetGridX = getGridX();
//                targetGridY = getGridY();
            }


        }

        super.update();

        lookAtMe();

    }

    private void tweenMe() {

        int oldX = getX();
        int oldY = getY();

        if (tweenToX > getX()) {
            setX(getX() + 2);
        }
        if (tweenToX < getX()) {
            setX(getX() - 2);
        }
        if (tweenToY > getY()) {
            setY(getY() + 2);
        }
        if (tweenToY < getY()) {
            setY(getY() - 2);
        }

        isTweening = false;
        if (oldX != getX()) isTweening = true;
        if (oldY != getY()) isTweening = true;

        lookAtMe();

    }

    private boolean gridIsEmpty(int gridX, int gridY) {

        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.getGridSize(), gridY * K.getGridSize());
        for (KEntity e : entities) {
            if (e.getGridX() == gridX) {
                if (e.getGridY() == gridY) {

                    if (e.isSolid()) {
                        KLog.trace("grid is not empty due to " + e.getClass().getSimpleName());

                        return false;
                    }
                }
            }
        }
        return true;
    }

    private KEntity findFirstEntityOnGrid(Class clazz, int gridX, int gridY) {
        List<KEntity> entities = getRoom().findEntitiesAtPoint(gridX * K.getGridSize(), gridY * K.getGridSize());
        for (KEntity e : entities) {
            if (e.getGridX() == gridX) {
                if (e.getGridY() == gridY) {
                    if (clazz.isInstance(e)) return e;
                }
            }
        }
        return null;
    }

    private void doPathFinding() {

        GridMap pathMap = createPathMap();

        GridPathfinding gridPathfinding = new GridPathfinding();


        int startX = getGridX();
        int startY = getGridY();
        int endX = targetGridX;
        int endY = targetGridY;

        KLog.pathfinder(this, "get path from " + startX + "," + startY + " to " + endX + "," + endY);

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
