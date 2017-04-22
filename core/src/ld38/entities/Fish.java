package ld38.entities;

import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Fish extends TrollCastleEntityBase {
    @Override
    public void onClick() {
        describe("That looks like a nice spot to try some fishing.");
    }
}
