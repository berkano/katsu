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
    private int money = 10;
    boolean hasWon = false;

    boolean isTweening = false;
    int tweenToX = 0;
    int tweenToY = 0;
    private boolean hasDoneStupidQuote = false;

    private Stage stage = Stage.normal;

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

    public enum Stage {

        normal(0),
        scarf(500),
        gold(1000);

        int minMoney;

        Stage(int minMoney) {
            this.minMoney = minMoney;
        }

        int getMinMoney() {
            return minMoney;
        }
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
        setZLayer(100);

    }

    @Override
    public void firstUpdate() {
        setTextureFrom(Tree.class);
    }

    @Override
    public void render() {

        //KLog.trace("Snowman instance " + toString() + " rendering");
        super.render();
    }


    @Override
    public void update() {

        super.update();

        if (isTweening) {
            tweenMe();
            return;
        }

        KDirection suggestedDirection = doPathFinding(targetGridX, targetGridY);
        if (suggestedDirection != null) {
            nextDX = suggestedDirection.dx();
            nextDY = suggestedDirection.dy();
        }

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

                        if (toChop.burnt) {
                            K.getUI().writeText("Poor tree and poor me. It got burnt so I can't sell it.");
                            if (!hasDoneStupidQuote) {
                                if (K.random.nextInt(5) == 0) {
                                    K.getUI().writeText("");
                                    K.getUI().writeText("Burned is the older form. Burnt came about during a period in the 16th through 18th centuries");
                                    K.getUI().writeText("in which there was a trend toward replacing -ed endings with -t in words where -ed was no");
                                    K.getUI().writeText("longer pronounced as a separate syllable.");
                                    K.getUI().writeText("");
                                    hasDoneStupidQuote = true;
                                }
                            }

                        } else {

                            int earned = toChop.getMarketValue();
                            money += earned;
                            K.getUI().writeText("Yay, I earned £" + earned + "!");
                        }

                        toChop.destroy();

                        // put out le fires
                        Fire toPutOut = (Fire)findFirstEntityOnGrid(Fire.class, targetGridX, targetGridY);
                        if (toPutOut != null) {
                            toPutOut.destroy();
                        }

                        performedAction = true;
                    }
                }
                if (targetAction == Action.PLANT) {

                    boolean hasGrass = false;
                    // only allow planting where there is Grass
                    if (findFirstEntityOnGrid(Grass.class,targetGridX, targetGridY) != null) {
                        hasGrass = true;
                    }

                    if (gridIsEmpty(targetGridX, targetGridY) && hasGrass) {

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
                            K.getUI().writeText("I don't have enough money :-( I need £1. I'd better go chop some trees!");
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
                            K.getUI().writeText("I don't have enough money :-( I need £100. I'd better go chop some trees!");
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

        updateStageAndWinState();

        super.update();

        lookAtMe();

    }

    private void updateStageAndWinState() {

        Stage shouldBe = stage;

        for (Stage s: Stage.values()) {
            if (s.getMinMoney() <= getMoney()) {
                shouldBe = s;
            }
        }

        if (shouldBe != stage) {
            boolean downgrade = false;
            if (shouldBe == Stage.normal) downgrade = true;
            if (shouldBe == Stage.scarf && stage == Stage.gold) downgrade = true;
            if (!downgrade) {
                K.getUI().writeText("Woo hoo! I got an upgrade!!");
                LD34Sounds.buy_land.play();
                K.pauseGame();
            } else {
                K.getUI().writeText("Sad face, I got a downgrade :-(");
                LD34Sounds.gone_wrong.play();
            }
            stage = shouldBe;
            if (stage == Stage.normal) setTextureFrom(Snowman.class);
            if (stage == Stage.gold) setTextureFrom(SnowmanGold.class);
            if (stage == Stage.scarf) setTextureFrom(SnowmanScarf.class);
        }

        // check win state
        if (!hasWon) {
            if (getMoney() >= 2000) {
                hasWon = true;
                LD34Sounds.buy_land.play();
                K.getUI().writeText("Woo hoo! I can retire!");
                K.getUI().writeText("You earned enough money for snowman to retire. Thanks for playing!");
                K.pauseGame();
            }
        }


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

}
