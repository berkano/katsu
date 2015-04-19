package ld32.entities;

import panko.PankoEntity;

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

        ArrayList<PankoEntity> entities = getRoom().findEntitiesAtPoint(getX() + getWidth()/2, getY() + getWidth() / 2);

        for (PankoEntity e : entities) {
            if (e instanceof Mole) {
                moleAlreadyAllowed = true;
            }
        }

    }
}
