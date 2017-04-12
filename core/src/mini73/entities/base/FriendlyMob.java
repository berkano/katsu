package mini73.entities.base;

import katsu.K;
import katsu.KRoom;
import mini73.entities.mobs.PlayerPerson;
import mini73.entities.mobs.Ship;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
public class FriendlyMob extends Mob {

    public FriendlyMob() {
        this.setSolid(true);
        this.isCollisionTarget = true;
        this.orientSpriteByMovement = false;
    }

    @Override
    public void beforeDeath(KRoom room) {
        super.beforeDeath(room);    //To change body of overridden methods use File | Settings | File Templates.


    }

    @Override
    public void update() {
        super.update();    //To change body of overridden methods use File | Settings | File Templates.

        if (K.random.nextInt(20) == 0) {
            if (!(this instanceof Ship)) {
                if (!(this instanceof PlayerPerson)) {
                    randomMove();
                }
            }
        }

    }

    private void randomMove() {

        if (System.currentTimeMillis() - getLastMove() < 100) return;

        int dir = K.random.nextInt(4);
        switch (dir) {
            case 0:
                tryMoveGridRelative(0, 1);
                break;
            case 1:
                tryMoveGridRelative(0, -1);
                break;
            case 2:
                tryMoveGridRelative(1, 0);
                break;
            case 3:
                tryMoveGridRelative(-1, 0);
                break;
        }


    }

}
