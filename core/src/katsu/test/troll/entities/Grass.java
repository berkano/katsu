package katsu.test.troll.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Grass extends TrollCastleEntityBase {

    public Grass(){
        getAppearance().juiceMySprite(0.2f);
    }

    @Override
    public void onClick() {
        describe("Some lovely green grass.");
    }

}
