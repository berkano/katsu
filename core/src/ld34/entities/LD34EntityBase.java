package ld34.entities;

import katsu.K;
import katsu.KEntity;
import katsu.KLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by shaun on 12/12/2015.
 */
public class LD34EntityBase extends KEntity {

    Logger logger = LoggerFactory.getLogger(LD34EntityBase.class);

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        logger.trace("LD34EntityBase detected touch down at " + screenX + "," + screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
