package katsu;

import com.badlogic.gdx.math.Rectangle;

import java.util.List;

/**
 * Created by shaun on 16/11/2014.
 */
public class KMovementConstrainer {

    public static boolean moveEntityIfPossible(KEntity entity, int newX, int newY) {

        if (K.gamePaused()) return false;

        long millisMovedAgo = K.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) return false;

        boolean couldMove = true;

        if (entity.isSolid()) {

            net.sf.jsi.Rectangle newRect = new net.sf.jsi.Rectangle(newX, newY, newX + entity.getWidth() - 1, newY - entity.getHeight() + 1);
            List<KEntity> overlappingEntities = entity.getRoom().spatialSearchByIntersection(newRect);

            // Get all possible collision targets
            for (KEntity other : overlappingEntities) {
                if (other.isSolid() && other.canCollideWith(entity.getClass()) && entity.canCollideWith(other.getClass())) {
                        couldMove = false;
                        entity.onCollide(other);
                        other.onCollide(entity);
                }
            }
        }

        if (couldMove) {
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }
}
