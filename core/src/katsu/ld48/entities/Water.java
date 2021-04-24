package katsu.ld48.entities;

import katsu.K;
import katsu.graphics.KAppearance;
import katsu.model.KTiledMapEntity;
import katsu.test.troll.entities.TrollCastleEntityBase;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Water extends LD48EntityBase {

    int rotateDir = 1;

    public Water(){
        super();
        getAppearance().juiceMySprite(0.5f);
        setSolid(false);
        if (K.random.nextBoolean()) {
            rotateDir = -1;
        }
    }



    @Override
    public void onClick() {
        describe("A bit watery");
    }

    @Override
    public void render() {
        KAppearance appearance = this.getAppearance();
        if (K.random.nextInt(10) == 0) {
            appearance.setSpriteRotation(appearance.getSpriteRotation() + rotateDir);
        }
        super.render();
    }
}
