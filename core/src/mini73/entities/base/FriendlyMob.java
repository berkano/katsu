package mini73.entities.base;

import com.badlogic.gdx.Application;
import katsu.KRoom;
import mini73.Objective;
import mini73.entities.mobs.PlayerPerson;
import mini73.entities.mobs.Ship;
import mini73.rooms.MainRoom;

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
    public void update(Application gc) {
        super.update(gc);    //To change body of overridden methods use File | Settings | File Templates.

        if (Katsu.random.nextInt(20) == 0) {
            if (!(this instanceof Ship)) {
                if (!(this instanceof PlayerPerson)) {
                    randomMove();
                }
            }
        }

        if (Katsu.random.nextInt(100) == 0) {
            MainRoom mr = (MainRoom) Katsu.game.currentRoom;
        }

        if (currentObjective == Objective.NOTHING) {


        }

    }
}
