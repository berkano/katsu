package ld34.entities;

import katsu.*;
import ld34.LD34Sounds;

/**
 * Created by shaun on 12/12/2015.
 */
public class Snowman extends LD34EntityBase {

    long didLastPathFind = K.utils.currentTime();
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
        getAppearance().setRotateSpriteOnMove(false);
        getAppearance().setFlipSpriteOnMove(true);
        setMaxMoveInterval(100);
        getAppearance().setZLayer(100);

    }

    @Override
    public void firstUpdate() {
        getAppearance().setTextureFrom(Tree.class);
    }

    @Override
    public void render() {

        //KLogger.trace("Snowman instance " + toString() + " rendering");
        super.render();
    }


    @Override
    public void update() {

        super.update();

        if (isTweening) {
            tweenMe();
            return;
        }

        KDirection suggestedDirection = getGrid().doPathFinding(targetGridX, targetGridY);
        if (suggestedDirection != null) {
            nextDX = suggestedDirection.getDx();
            nextDY = suggestedDirection.getDy();
        }

        if (hasTarget) {

            // Do the moving first or we get in a tangle
            if (nextDX != 0 || nextDY != 0) {
                boolean almostThere = false;
                // if our action should be performed adjacent to the target, don't go all the way there.
                if (getGrid().getX() + nextDX == targetGridX && getGrid().getY() + nextDY == targetGridY) {
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
                    if (tryMove(nextDX, nextDY)) {
                        isTweening = true;
                        setX(oldX);
                        setY(oldY);
                        tweenToX=(oldX + nextDX * K.settings.getGridSize());
                        tweenToY=(oldY + nextDY * K.settings.getGridSize());

                        LD34Sounds.walk.play();
                    }
                }
                didLastPathFind = K.utils.currentTime();
                hasDoneFirstPathFind = true;
                nextDX = 0;
                nextDY = 0;
            }

            // If we have an action and a target then check if we're adjacent and do it
            int dx = targetGridX - getGrid().getX();
            int dy = targetGridY - getGrid().getY();
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
                    Tree toChop = (Tree) getGrid().findFirstEntity(Tree.class, targetGridX, targetGridY);
                    if (toChop != null) {
//                        K.ui.writeText("Choppy chop!");
                        LD34Sounds.chop.play();

                        if (toChop.burnt) {
                            K.ui.writeText("Poor tree and poor me. It got burnt so I can't sell it.");
                            if (!hasDoneStupidQuote) {
                                if (K.random.nextInt(5) == 0) {
                                    K.ui.writeText("");
                                    K.ui.writeText("Burned is the older form. Burnt came about during a period in the 16th through 18th centuries");
                                    K.ui.writeText("in which there was a trend toward replacing -ed endings with -t in words where -ed was no");
                                    K.ui.writeText("longer pronounced as a separate syllable.");
                                    K.ui.writeText("");
                                    hasDoneStupidQuote = true;
                                }
                            }

                        } else {

                            int earned = toChop.getMarketValue();
                            money += earned;
                            K.ui.writeText("Yay, I earned £" + earned + "!");
                        }

                        toChop.destroy();

                        // put out le fires
                        Fire toPutOut = (Fire)getGrid().findFirstEntity(Fire.class, targetGridX, targetGridY);
                        if (toPutOut != null) {
                            toPutOut.destroy();
                        }

                        performedAction = true;
                    }
                }
                if (targetAction == Action.PLANT) {

                    boolean hasGrass = false;
                    // only allow planting where there is Grass
                    if (getGrid().findFirstEntity(Grass.class,targetGridX, targetGridY) != null) {
                        hasGrass = true;
                    }

                    if (getGrid().isEmpty(targetGridX, targetGridY) && hasGrass) {

                        if (money >= 1) {

                            Tree sapling = new Tree();
                            sapling.getGrid().setX(targetGridX);
                            sapling.getGrid().setY(targetGridY);
                            sapling.setStage(Tree.Stage.sapling);
                            getRoom().addNewEntity(sapling);
//                            K.ui.writeText("Planty plant!");
                            LD34Sounds.plant.play();

                            // don't let snowman be on top of tree
                            targetGridX = getGrid().getX();
                            targetGridY = getGrid().getY();
                            money = money - 1;
                        } else {
                            K.ui.writeText("I don't have enough money :-( I need £1. I'd better go chop some trees!");
                            LD34Sounds.gone_wrong.play();
                        }

                    } else {
                        K.ui.writeText("I can't plant here :-(");
                        LD34Sounds.gone_wrong.play();
                    }

                    performedAction = true;

                }
                if (targetAction == Action.BUY_LAND) {
                    Land land = (Land) getGrid().findFirstEntity(Land.class, targetGridX, targetGridY);
                    if (land == null) {
                        K.ui.writeText("There's no land to buy here :-(");
                        LD34Sounds.gone_wrong.play();
                    } else {
                        if (money < 100) {
                            K.ui.writeText("I don't have enough money :-( I need £100. I'd better go chop some trees!");
                            LD34Sounds.gone_wrong.play();
                        } else {
                            Grass grass = new Grass();
                            grass.setX(land.getX());
                            grass.setY(land.getY());
                            getRoom().addNewEntity(grass);
                            land.destroy();
                            money -= 100;
                            K.ui.writeText("New land! Woo!");
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
                K.ui.writeText("Woo hoo! I got an upgrade!!");
                LD34Sounds.buy_land.play();
                K.runner.pauseGame();
            } else {
                K.ui.writeText("Sad face, I got a downgrade :-(");
                LD34Sounds.gone_wrong.play();
            }
            stage = shouldBe;
            if (stage == Stage.normal) getAppearance().setTextureFrom(Snowman.class);
            if (stage == Stage.gold) getAppearance().setTextureFrom(SnowmanGold.class);
            if (stage == Stage.scarf) getAppearance().setTextureFrom(SnowmanScarf.class);
        }

        // check win state
        if (!hasWon) {
            if (getMoney() >= 2000) {
                hasWon = true;
                LD34Sounds.buy_land.play();
                K.ui.writeText("Woo hoo! I can retire!");
                K.ui.writeText("You earned enough money for snowman to retire. Thanks for playing!");
                K.runner.pauseGame();
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
