package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class RockSurfaceBottom extends LD48EntityBase {

    public RockSurfaceBottom(){
        super();
        getAppearance().juiceMySpriteNoFlips(0.2f);
        setSolid(false);
    }

}
