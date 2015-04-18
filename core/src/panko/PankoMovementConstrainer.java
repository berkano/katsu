package panko;

import com.badlogic.gdx.math.Rectangle;
import ld32.entities.Worm;

/**
 * Created by shaun on 16/11/2014.
 */
public class PankoMovementConstrainer {

    public static boolean moveEntityIfPossible(PankoEntity entity, int newX, int newY) {

        long millisMovedAgo = Panko.currentTime() - entity.getLastMove();
        if (millisMovedAgo < entity.getMaxMoveInterval()) return false;

        boolean couldMove = true;

        if (entity.isSolid()) {

            // First get the room
            PankoRoom room = entity.getRoom();

            // Get all possible collision targets
            for (PankoEntity other : room.getEntities()) {
                if (other.isSolid()) {
                    if (entitiesWouldOverlap(entity, newX, newY, other)) {
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
            entity.setLastMove(Panko.currentTime());
            entity.onMoved();
        }

        return couldMove;

    }

    private static boolean entitiesWouldOverlap(PankoEntity entity, int newX, int newY, PankoEntity other) {

        // Quick optimisation for obvious cases
        if (newX > entity.getX() + other.getWidth()) return false;
        if (newY > entity.getY() + other.getHeight()) return false;
        if (newX < entity.getX() - other.getWidth()) return false;
        if (newY < entity.getY() - other.getHeight()) return false;

        // Bounding box intersection calculation
        Rectangle r1 = new Rectangle(newX, newY, entity.getWidth(), entity.getHeight());
        Rectangle r2 = new Rectangle(other.getX(), other.getY(), other.getWidth(), other.getHeight());

        return r1.overlaps(r2);

    }
}
