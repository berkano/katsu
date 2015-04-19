package ld32.entities;

/**
 * Created by shaun on 18/04/2015.
 */
public class OpenDoor extends SolidEntity {
    @Override
    public boolean canCollideWith(Class clazz) {
        if (clazz == Mole.class) return false;
        return super.canCollideWith(clazz);
    }
}
