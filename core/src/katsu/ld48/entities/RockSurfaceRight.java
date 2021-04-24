package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class RockSurfaceRight extends LD48EntityBase {

    public RockSurfaceRight(){
        super();
        getAppearance().juiceMySprite(0.2f);
        setSolid(true);
    }

}