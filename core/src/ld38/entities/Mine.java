package ld38.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Mine extends TrollCastleEntityBase {
    @Override
    public void onClick() {
        describe("Untold riches and dangers await the intrepid miner.");
    }
}
