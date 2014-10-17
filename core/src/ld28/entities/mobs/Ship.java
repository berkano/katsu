package ld28.entities.mobs;

import ld28.Sounds;
import ld28.entities.base.FriendlyMob;
import ld28.rooms.MainRoom;

/**
 * @author shaun
 */
public class Ship extends FriendlyMob {

    public Ship() {
        super();
        this.orientSpriteByMovement = true;
    }

    @Override
    public void afterMoved() {
        super.afterMoved();

        long lastMovedMillis = System.currentTimeMillis() - lastMoved;
        if (lastMovedMillis > 5000) {
            Sounds.engine.play();
        }
        ((MainRoom)room).gameState.fuel -= 1;


    }
}
