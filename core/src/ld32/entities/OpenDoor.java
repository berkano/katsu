package ld32.entities;

import katsu.KEntity;

import java.util.ArrayList;

/**
 * Created by shaun on 18/04/2015.
 */
public class OpenDoor extends SolidEntity {

    boolean moleAlreadyAllowed = false;

    @Override
    public boolean canCollideWith(Class clazz) {
        if (clazz == Mole.class) {
            return moleAlreadyAllowed;
        }
        return super.canCollideWith(clazz);
    }

    @Override
    public void update() {
        super.update();

        ArrayList<KEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth()/2, getY() + getWidth() / 2);

        for (KEntity e : entities) {
            if (e instanceof Mole) {
                moleAlreadyAllowed = true;
            }
        }

    }
}
