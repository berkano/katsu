package ld38.entities;

import katsu.KEntity;
import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Water extends TrollCastleEntityBase {

    public Water(){
        super();
        getAppearance().juiceMySprite(0.2f);
        setSolid(true);
    }

    @Override
    public void onClick() {
        describe("The water is wet today.");
    }

}
