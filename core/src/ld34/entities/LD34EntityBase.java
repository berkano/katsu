package ld34.entities;

import katsu.KEntity;
import katsu.KLog;

/**
 * Created by shaun on 12/12/2015.
 */
public class LD34EntityBase extends KEntity {

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        KLog.trace("LD34EntityBase detected touch down at " + screenX + "," + screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
