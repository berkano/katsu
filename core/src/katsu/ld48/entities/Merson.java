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
            tryMoveAbsolutePoint(getX(), getY() + 1);
        } else {
            tryMoveAbsolutePoint(getX(), getY() - 1);
        }

        if (K.input.isKeyDown(Input.Keys.LEFT)) {
            tryMoveAbsolutePoint(getX() - 1, getY());
            getAppearance().setSpriteFlip(false);
        }
        if (K.input.isKeyDown(Input.Keys.RIGHT)) {
            tryMoveAbsolutePoint(getX() + 1, getY());
            getAppearance().setSpriteFlip(true);
        }

    }

}
