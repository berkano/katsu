package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Submarine extends LD48EntityBase {

    @Override
    public void onClick() {
        describe("We all live here");
    }

}
