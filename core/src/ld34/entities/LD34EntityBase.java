package ld34.entities;

import katsu.K;
import katsu.KEntity;
import katsu.KLogger;

/**
 * Created by shaun on 12/12/2015.
 */
public class LD34EntityBase extends KEntity {

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        K.logger.trace("LD34EntityBase detected touch down at " + screenX + "," + screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
