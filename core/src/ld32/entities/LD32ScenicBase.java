package ld32.entities;

import katsu.K;
import katsu.KEntity;

/**
 * Created by shaun on 19/04/2015.
 */
public class LD32ScenicBase extends KEntity {

    public LD32ScenicBase() {
        super();
        this.setSpriteRotation(K.random.nextInt(20) - 10);

    }

}
