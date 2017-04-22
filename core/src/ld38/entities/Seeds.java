package ld38.entities;

import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Seeds extends TrollCastleEntityBase {
    @Override
    public void onClick() {
        describe("Seeds can be grown to provide crops.");
    }
}
