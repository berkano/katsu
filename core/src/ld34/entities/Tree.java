package ld34.entities;

import katsu.K;

/**
 * Created by shaun on 12/12/2015.
 */
public class Tree extends LD34EntityBase {


    int age = 0; // seconds
    long lastSecond = K.utils.currentTime();
    boolean burnt = false;

    public int getMarketValue() {
        if (stage == Stage.sapling) return 0;
        if (stage == Stage.large) return 45;
        if (stage == Stage.medium) return 20;
        if (stage == Stage.small) return 7;
        throw new RuntimeException("Can't get market value for tree stage " + stage);
    }

    public void setOnFire() {
        if (!burnt) {
            burnt = true;
            Fire fire = new Fire();
            fire.setX(getX());
            fire.setY(getY());
            getRoom().addNewEntity(fire);
//            K.ui.writeText("I AM A TREE AND I AM ON FIRE!!!");
        }
    }

    enum Stage {

        sapling(0), // seconds old at beginning of stage
        small(10),
        medium(20),
        large(60);

        int beginsAt = 0;

        Stage(int beginsAt) {
            this.beginsAt = beginsAt;
        }

    }

    private Stage stage;

    public Tree() {
        super();
        setStage(Stage.sapling);
        setSolid(true);
    }

    @Override
    public void update() {
        super.update();
        if (K.utils.currentTime() > lastSecond + 1000) {
            lastSecond = K.utils.currentTime();
            handleSecond();
        }
    }

    private void handleSecond() {

        age += 1;
        Stage iShouldBe = stage;

        for (Stage s : Stage.values()) {
            if (age >= s.beginsAt) {
                iShouldBe = s;
            }
        }

        if (iShouldBe != stage) {
            setStage(iShouldBe);
        }

    }

    public void setStage(Stage stage) {

        K.logger.trace("Tree " + toString() + " set to stage " + stage);

        this.stage = stage;
        if (stage == Stage.sapling) {
            getAppearance().setTextureRegion(K.ui.getTextureCache().get(Sapling.class));
        }
        if (stage == Stage.small) {
            getAppearance().setTextureRegion(K.ui.getTextureCache().get(TreeSmall.class));
        }
        if (stage == Stage.medium) {
            getAppearance().setTextureRegion(K.ui.getTextureCache().get(TreeMedium.class));
        }
        if (stage == Stage.large) {
            getAppearance().setTextureRegion(K.ui.getTextureCache().get(TreeLarge.class));
        }

        age = stage.beginsAt;
    }


}
