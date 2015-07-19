package ld32.entities;

import katsu.K;
import katsu.KEntityBase;

/**
 * Created by shaun on 19/04/2015.
 */
public class LD32ScenicBase extends KEntityBase {

    public LD32ScenicBase() {
        super();
        this.setSpriteRotation(K.random.nextInt(20) - 10);

    }

}
