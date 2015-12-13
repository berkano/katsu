package ld34.entities;

import katsu.K;
import katsu.KLog;

/**
 * Created by shaun on 12/12/2015.
 */
public class Tree extends LD34EntityBase {



    enum Stage {
        sapling,
        small,
        medium,
        large
    }

    private Stage stage;

    public Tree() {
        super();
        setStage(Stage.sapling);
        setSolid(true);
    }

    public void setStage(Stage stage) {

        KLog.trace("Tree " + toString() + " set to stage " + stage);

        this.stage = stage;
        if (stage == Stage.sapling) {
            setTextureRegion(K.getUI().getTextureCache().get(Sapling.class));
        }
        if (stage == Stage.small) {
            setTextureRegion(K.getUI().getTextureCache().get(TreeSmall.class));
        }
        if (stage == Stage.medium) {
            setTextureRegion(K.getUI().getTextureCache().get(TreeMedium.class));
        }
        if (stage == Stage.large) {
            setTextureRegion(K.getUI().getTextureCache().get(TreeLarge.class));
        }
    }


}
