package ld32.entities;

import katsu.K;
import katsu.KDirection;

/**
 * Created by shaun on 18/04/2015.
 */
public class SolidRotatingTerrain extends LD32ScenicBase {

    public SolidRotatingTerrain() {
        super();
        setSolid(true);
        setSpriteRotation(KDirection.random().rotation() + K.random.nextInt(20) - 10);
    }

}
