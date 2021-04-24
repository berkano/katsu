package katsu.ld48.entities;

import com.badlogic.gdx.Input;
import katsu.K;
import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Merson extends LD48EntityBase {

    public Merson() {
        super();
        setSolid(true);
    }

    @Override
    public void update() {
        super.update();
        lookAtMe();

        if (K.input.isKeyDown(Input.Keys.SPACE)) {
            setY(getY()+1);
        } else {
            setY(getY()-1);
        }

        if (K.input.isKeyDown(Input.Keys.LEFT)) {
            setX(getX()-1);
            getAppearance().setSpriteFlip(false);
        }
        if (K.input.isKeyDown(Input.Keys.RIGHT)) {
            setX(getX()+1);
            getAppearance().setSpriteFlip(true);
        }

    }

}
