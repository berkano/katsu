package ld28.entities.base;

import com.badlogic.gdx.Application;
import katsu.Katsu;
import katsu.Room;
import katsu.Objective;
import katsu.entities.Mob;
import ld28.entities.mobs.PlayerPerson;
import ld28.entities.mobs.Ship;
import ld28.rooms.MainRoom;

/**
 * Created with IntelliJ IDEA.
 * User: shaun
 * Date: 24/08/13
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
public class FriendlyMob extends Mob {

    public FriendlyMob() {
        this.solid = true;
        this.isCollisionTarget = true;
        this.orientSpriteByMovement = false;
    }

    @Override
    public void beforeDeath(Room room) {
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
