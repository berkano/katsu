package ld38.entities;

import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Mushroom extends TrollCastleEntityBase {

    @Override
    public void onClick() {
        describe("Trolls love Mushrooms. Just don't let them have too many.");
    }


}