package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;
import katsu.test.troll.entities.TrollCastleEntityBase;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Fish extends LD48EntityBase {

    @Override
    public void onClick() {
        describe("A bit fishy");
    }

}
