package ld32.entities;

import panko.PankoDirection;
import panko.PankoEntityBase;

/**
 * Created by shaun on 18/04/2015.
 */
public class SolidRotatingTerrain extends PankoEntityBase {

    public SolidRotatingTerrain() {
        super();
        setSolid(true);
        setSpriteRotation(PankoDirection.random().rotation());
    }

}
