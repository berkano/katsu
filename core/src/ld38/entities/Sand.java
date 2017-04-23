package ld38.entities;

import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Sand extends TrollCastleEntityBase {

    public Sand(){
        getAppearance().juiceMySprite(0.2f);
    }

    @Override
    public void onClick() {
        describe("This sand looks sandy.");
    }

}