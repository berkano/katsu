package ld33.entities;

import katsu.KDirection;
import katsu.KGraphics;

/**
 * Created by shaun on 22/08/2015.
 */
public class Monster extends MobBase {

    private boolean looksHuman = true;

    public Monster()
    {
        super();
        this.setSolid(true);
        this.setRotateSpriteOnMove(false);
        this.setFlipSpriteOnMove(true);
        this.setMaxMoveInterval(75);
        this.setzLayer(100);
    }

    @Override
    public void render() {
        if (looksHuman) {
            setTextureRegion(KGraphics.getTextureCache().get(Human.class));
        } else {
            setTextureRegion(KGraphics.getTextureCache().get(Monster.class));
        }
        lookAtMe();
        super.render();
    }

    @Override
    public boolean moveRequested(KDirection direction) {
        boolean result = super.moveRequested(direction);
        lookAtMe();
        return result;
    }

    public boolean isLooksHuman() {
        return looksHuman;
    }

    public void setLooksHuman(boolean looksHuman) {
        this.looksHuman = looksHuman;
    }
}
