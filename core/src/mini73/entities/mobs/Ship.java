package mini73.entities.mobs;

import katsu.KTiledMapEntity;
import mini73.entities.base.FriendlyMob;

/**
 * @author shaun
 */
@KTiledMapEntity
public class Ship extends FriendlyMob {

    public Ship() {
        super();
        this.orientSpriteByMovement = true;
    }

//    @Override
//    public void afterMoved() {
//        super.afterMoved();
//
//        long lastMovedMillis = System.currentTimeMillis() - lastMoved;
//        if (lastMovedMillis > 5000) {
//            Sounds.engine.play();
//        }
//        ((MainRoom)room).gameState.fuel -= 1;
//
//
//    }
}
