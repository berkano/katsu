package katsu.test.troll.entities;

import katsu.model.KTiledMapEntity;
import katsu.test.troll.TrollDevFlags;

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
        int growTime = 30000;
        if (TrollDevFlags.quickMushGrow) {
            growTime = 3000;
        }
        if (createdMillis < System.currentTimeMillis() - growTime) {
            destroy();
            Mushroom mushroom = new Mushroom();
            mushroom.setX(getX());
            mushroom.setY(getY());
            getRoom().addNewEntity(mushroom);
            sounds.grow.play();
        }
    }
}
