package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;
import katsu.test.troll.entities.TrollCastleEntityBase;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Water extends LD48EntityBase {

    public Water(){
        super();
        getAppearance().juiceMySprite(0.2f);
        setSolid(false);
    }

    @Override
    public void onClick() {
        describe("A bit watery");
    }

}
