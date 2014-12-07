package ld31v2.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 07/12/2014.
 */
public class FuzzyTerrain extends PankoEntityBase {

    public FuzzyTerrain() {
        super();
        int sprot = Panko.random.nextInt(12) - 6;
        if (this instanceof Grass || this instanceof Dirt || this instanceof Water) {
            sprot = sprot + Panko.random.nextInt(4) * 90;
            if (sprot > 360) sprot -= 360;
        }
        setSpriteRotation(sprot);
        setSpriteScale(1.1f);
    }

}
