package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class RockSurfaceLeft extends LD48EntityBase {

    public RockSurfaceLeft(){
        super();
        getAppearance().juiceMySpriteNoFlips(0.2f);
        setSolid(false);
    }

}
