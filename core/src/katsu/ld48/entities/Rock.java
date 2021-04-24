package katsu.ld48.entities;

import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Rock extends LD48EntityBase {

    public Rock(){
        super();
        getAppearance().juiceMySprite(0.2f);
        setSolid(true);
    }

}
