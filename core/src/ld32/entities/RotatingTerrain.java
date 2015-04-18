package ld32.entities;

import panko.PankoDirection;
import panko.PankoEntity;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class RotatingTerrain extends PankoEntityBase {

    public RotatingTerrain() {
        super();
        setSpriteRotation(PankoDirection.random().rotation());
    }

}
