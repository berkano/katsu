package ld32.entities;

import katsu.PankoDirection;
import katsu.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class RotatingTerrain extends PankoEntityBase {

    public RotatingTerrain() {
        super();
        setSpriteRotation(PankoDirection.random().rotation());
    }

}
