package ld32.entities;

import katsu.Panko;
import katsu.PankoEntityBase;

/**
 * Created by shaun on 19/04/2015.
 */
public class LD32ScenicBase extends PankoEntityBase {

    public LD32ScenicBase() {
        super();
        this.setSpriteRotation(Panko.random.nextInt(20) - 10);

    }

}
