package ld32.entities;

import katsu.KDirection;
import katsu.KEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class RotatingTerrain extends KEntityBase {

    public RotatingTerrain() {
        super();
        setSpriteRotation(KDirection.random().rotation());
    }

}
