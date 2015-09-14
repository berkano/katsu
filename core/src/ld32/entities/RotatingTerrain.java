package ld32.entities;

import katsu.KDirection;
import katsu.KEntity;

/**
 * Created by shaun on 18/04/2015.
 */
public class RotatingTerrain extends KEntity {

    public RotatingTerrain() {
        super();
        setSpriteRotation(KDirection.random().rotation());
    }

}
