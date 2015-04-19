package ld32.entities;

import panko.Panko;
import panko.PankoEntityBase;

/**
 * Created by shaun on 19/04/2015.
 */
public class LD32ScenicBase extends PankoEntityBase {

    public LD32ScenicBase() {
        super();
        this.setSpriteRotation(Panko.random.nextInt(20) - 10);

    }

}
