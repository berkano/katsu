package ld31v2.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 06/12/2014.
 */
public class Water extends FuzzyTerrain {

    int initialRotation;

    public Water() {
        super();
        this.setSolid(true);
        initialRotation = getSpriteRotation();
    }

    @Override
    public void update() {
        super.update();
        if ((Panko.random.nextInt(10)) == 0) {
            setSpriteRotation(initialRotation + Panko.random.nextInt(4) - 2);
        }
    }
}
