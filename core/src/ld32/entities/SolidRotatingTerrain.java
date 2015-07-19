package ld32.entities;

import katsu.Panko;
import katsu.PankoDirection;

/**
 * Created by shaun on 18/04/2015.
 */
public class SolidRotatingTerrain extends LD32ScenicBase {

    public SolidRotatingTerrain() {
        super();
        setSolid(true);
        setSpriteRotation(PankoDirection.random().rotation() + Panko.random.nextInt(20) - 10);
    }

}
