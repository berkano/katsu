package katsu;

import java.util.List;

/**
 * Created by shaun on 29/02/2016.
 */
public class KRules {

    public boolean moveEntityIfPossible(KEntity entity, int newX, int newY) {

        if (K.runner.gamePaused()) {
            return false;
        }

        long millisMovedAgo = K.utils.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) {
            return false;
        }

        boolean couldMove = true;

        if (entity.isSolid()) {

            net.sf.jsi.Rectangle newRect = new net.sf.jsi.Rectangle(newX, newY, newX + entity.getWidth() - 1, newY - entity.getHeight() + 1);
            List<KEntity> overlappingEntities = entity.getRoom().spatialSearchByIntersection(newRect);

            // Get all possible collision targets
            for (KEntity other : overlappingEntities) {
                if (other.isSolid() && other.canCollideWith(entity.getClass()) && entity.canCollideWith(other.getClass())) {
                    if (other != entity) {
                        couldMove = false;
                        entity.onCollide(other);
                        other.onCollide(entity);
                    }
                }
            }
        }

        if (couldMove) {
            entity.setX(newX);
            entity.setY(newY);
            entity.setLastMove(K.utils.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }

}
