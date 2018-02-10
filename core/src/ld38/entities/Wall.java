package ld38.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Wall extends TrollCastleEntityBase {
    @Override
    public void onClick() {
        describe("A wall to keep the Trolls safe.");
    }
}
