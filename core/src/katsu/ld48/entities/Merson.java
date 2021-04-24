package katsu.ld48.entities;

import com.badlogic.gdx.Input;
import katsu.K;
import katsu.model.KEntity;
import katsu.model.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class Merson extends LD48EntityBase {

    int moveSpeed = 1;

    public Merson() {
        super();
        setSolid(true);
    }

    @Override
    public void update() {
        super.update();
        lookAtMe();

        if (K.input.isKeyDown(Input.Keys.SPACE)) {
            tryMoveAbsolutePoint(getX(), getY() + moveSpeed);
        } else {
            tryMoveAbsolutePoint(getX(), getY() - moveSpeed);
        }

        if (K.input.isKeyDown(Input.Keys.LEFT)) {
            tryMoveAbsolutePoint(getX() - moveSpeed, getY());
            getAppearance().setSpriteFlip(false);
        }
        if (K.input.isKeyDown(Input.Keys.RIGHT)) {
            tryMoveAbsolutePoint(getX() + moveSpeed, getY());
            getAppearance().setSpriteFlip(true);
        }

        notifySharksOfPosition();

    }

    private void notifySharksOfPosition() {
        for (KEntity e : getRoom().getEntities()) {
            if (e instanceof Shark) {
                e.setTargetEntity(this);
            }
        }
    }

}
