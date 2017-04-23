package ld38.entities;

import katsu.KTiledMapEntity;

/**
 * Created by shaun on 21/04/2017.
 */
@KTiledMapEntity
public class BabyMushroom extends TrollCastleEntityBase {

    long createdMillis = System.currentTimeMillis();

    @Override
    public void onClick() {
        describe("A baby mushroom, which will one day grow into an intriguing snack.");
    }

    @Override
    public void update() {
        super.update();
        if (createdMillis < System.currentTimeMillis() - 60000) {
            destroy();
            Mushroom mushroom = new Mushroom();
            mushroom.setX(getX());
            mushroom.setY(getY());
            getRoom().addNewEntity(mushroom);
        }
    }
}
