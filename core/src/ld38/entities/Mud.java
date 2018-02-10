package ld38.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Mud extends TrollCastleEntityBase {

    public Mud(){
        getAppearance().juiceMySprite(0.2f);
    }

    @Override
    public void onClick() {
        describe("A muddy patch excellent for growing Mushrooms.");
    }

}
