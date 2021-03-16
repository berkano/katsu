package katsu.test.troll.entities;

import katsu.model.KTiledMapEntity;

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
        describe("This sand looks sandy. But you could probably build something on it.");
    }

}
